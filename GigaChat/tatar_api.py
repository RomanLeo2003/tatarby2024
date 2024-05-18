from fastapi import FastAPI
import uvicorn
from pydantic import BaseModel
from typing import List, Dict, Any
from tatar_assistant import TatarAssistant

app = FastAPI()

assistant = TatarAssistant()


class StoryGet(BaseModel):
    level: int
    prev_text: int
    words: List[str]

class StoryReturn(BaseModel):
    new_text: str
    options: List[str]

class Chat(BaseModel):
    user_message: str

prevka = ""
prev_story_sad = ""
prev_story_happy = ""


@app.post("/story/", response_model=StoryReturn)
def story(message: StoryGet):
    global prevka, prev_story_sad, prev_story_happy
    print(message.level, message.prev_text, message.words)
    if message.prev_text == 0:
        prevka = prev_story_sad
    else:
        prevka = prev_story_happy

    if message.level == 1:
        response = assistant.generate_first_text(message.words[:2])
        print(response)
        prev_story_sad = assistant.generate_sad_contin(message.words[2:4], response)
        print(prev_story_sad)
        prev_story_happy = assistant.generate_happy_contin(message.words[2:4], response)
        print(prev_story_happy)
    if message.level == 2:
        response = prevka
        prev_story_sad = assistant.generate_sad_end(message.words[-2:], response)
        prev_story_happy = assistant.generate_happy_end(message.words[-2:], response)
    if message.level == 3:
        response = prevka
        prev_story_sad = ""
        prev_story_happy = ""

    answer_sad = assistant.take_first_sent(prev_story_sad)
    answer_happy = assistant.take_first_sent(prev_story_happy)

    response_data = {
        "new_text": response,
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



@app.get("/healthcheck", tags=["healthcheck"])
def healthcheck():
    return {"status": "ok"}

if __name__ == "__main__":
    uvicorn.run(app, port=8000)
