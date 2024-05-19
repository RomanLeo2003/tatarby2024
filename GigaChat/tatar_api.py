from fastapi import FastAPI
import base64
import uvicorn
from pydantic import BaseModel
from typing import List, Dict, Any
from tatar_assistant import TatarAssistant
from asr import ASR_class
from T5 import T5
import wave
app = FastAPI()

assistant = TatarAssistant()
asr_class=ASR_class()
#t5=T5(model_name="")

class StoryGet(BaseModel):
    level: int
    prev_text: int
    words: List[str]

class StoryReturnF(BaseModel):
    new_text: str

class StoryReturnS(BaseModel):
    options: List[str]

class Chat(BaseModel):
    user_message: str

class Asr_data(BaseModel):
    audio: str
    text: str

prevka = ""
prev_story_sad = ""
prev_story_happy = ""


@app.post("/story/", response_model=StoryReturnF)
def story(message: StoryGet):
    global prevka, prev_story_sad, prev_story_happy
    response = ""
    ru_words = assistant.translate_words(message.words)
    if message.prev_text == 0:
        prevka = prev_story_sad
    else:
        prevka = prev_story_happy

    if message.level == 1:
        response = assistant.generate_first_text(ru_words[:2])
        # prev_story_sad = assistant.generate_sad_contin(ru_words[2:4], response)
        # prev_story_happy = assistant.generate_happy_contin(ru_words[2:4], response)
    if message.level == 2:
        response = prevka
        # prev_story_sad = assistant.generate_sad_end(ru_words[-2:], response)
        # prev_story_happy = assistant.generate_happy_end(ru_words[-2:], response)
    if message.level == 3:
        response = prevka
        # prev_story_sad = ""
        # prev_story_happy = ""

    # answer_sad = assistant.take_first_sent(prev_story_sad)
    # answer_happy = assistant.take_first_sent(prev_story_happy)
    prevka = response

    response_data = {
        "new_text": response,
        # "options": [answer_sad, answer_happy]
    }
    return response_data


@app.post("/story_cont/", response_model=StoryReturnS)
def story(message: StoryGet):
    global prevka, prev_story_sad, prev_story_happy
    response = ""
    ru_words = assistant.translate_words(message.words)
    if message.prev_text == 0:
        prevka = prev_story_sad
    else:
        prevka = prev_story_happy

    if message.level == 1:
        prev_story_sad = assistant.generate_sad_contin(ru_words[2:4], prevka)
        prev_story_happy = assistant.generate_happy_contin(ru_words[2:4], prevka)
    if message.level == 2:
        prev_story_sad = assistant.generate_sad_end(ru_words[-2:], prevka)
        prev_story_happy = assistant.generate_happy_end(ru_words[-2:], prevka)
    if message.level == 3:
        prev_story_sad = ""
        prev_story_happy = ""

    answer_sad = assistant.take_first_sent(prev_story_sad)
    answer_happy = assistant.take_first_sent(prev_story_happy)

    response_data = {
        "options": [answer_sad, answer_happy]
    }
    return response_data



@app.post("/chat/", response_model=Dict[str, Any])
def chat(message: Chat):
    response = assistant.chat(message.user_message)
    response_data = {
        "answer": response,
    }
    return response_data


@app.post("/asr/", response_model=Dict[str, Any])
def asr_feedback(data: Asr_data):
    text = data.text
    base64_audio= base64.b64decode(data.audio)
    output_file = "output.wav"
    with open(output_file, 'wb') as wf:
        wf.write(base64_audio)
    asr = asr_class.asr(filename=output_file)
    print(asr)
    print(text)
    msg = f'Оцени эти два текста на татарском языке: {asr}, {text}. Если тексты различаются, скажи пользователю, что все плохо, если они похожи, то скажи ему, что все хорошо. отвевай коротко, если все хорошо. если все плохо, добавь к ответу краткое обьяснение что не так, но при этому все равно короткий ответ. говори только про ошибки в первом предложении, так как второе исходно правильное. и первое предложение называй "Ваша запись"'
    response = assistant.chat(msg)
    response_data = {
        "answer": response,
    }
    return response_data

@app.post("/esse_cost/", description="это костыль для эссе, использует только гпт", response_model=Dict[str, Any])
def check_esse_cost(message: Chat):
    msg = "Проверь следующее эссе на наличие грамматических, орфографических, пунктуационных ошибок и подредактируй его не изменяя смысла: " + message.user_message
    response_errors = assistant.chat(msg)
    # msg_recense = "Напиши небольшую рецензию на эссе, обрати внимание на его смысл, расстановку акцентов, текст должен быть не слишком коротким: " + message.user_message
    response_recense = assistant.get_feedback(len(message.user_message))
    response_data = {
        "answer": "Небольшая рецензия: \n" + response_recense + "\n\n" + "Исправленный текст:\n" + response_errors,
    }
    return response_data

@app.post("/esse/", description="пока не работает",response_model=Dict[str, Any])
def check_esse(message: Chat):
    response_t5 = t5.inference(message.user_message)
    feedback = assistant.get_feedback()
    response = feedback + "\n" + response_t5
    response_data = {
        "answer": response,
    }
    return response_data

@app.get("/healthcheck", tags=["healthcheck"])
def healthcheck():
    return {"status": "ok"}

if __name__ == "__main__":
    uvicorn.run(app, port=8000)

