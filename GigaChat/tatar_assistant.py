import nltk
from typing import Dict, Any, List
from translatepy import Translator
from translatepy.translators.yandex import YandexTranslate
from random import randint

from langchain_openai import ChatOpenAI

OPENAI_MODEL='gpt-4'
OPENAI_API_KEY='sk-xKyGiSlLLZQDN7XVPhUDs3vQRLAAYYvV'
OPENAI_API_URL='https://api.proxyapi.ru/openai/v1'

class TatarAssistant:
    def __init__(self):
        self.llm = ChatOpenAI(
            model=OPENAI_MODEL,
            temperature=0,
            openai_api_base=OPENAI_API_URL,
            openai_api_key=OPENAI_API_KEY
        )

        self.yandex = YandexTranslate()

    def generate_first_text(self, words: List[str]) -> str:
        prompt = f"Write a super super short story. \
        There must be words in the story: {words}."
        llm_response = self.llm.invoke(prompt)
        response = self.yandex.translate(llm_response.content, "Tatar")
        return response.result
    
    def generate_sad_contin(self, words: List[str], prev_story: str) -> str:
        prompt = f"Write a super super short sad continuation of the story without an ending, using the words: {words} \
        The beginning of the story: {prev_story}"
        llm_response = self.llm.invoke(prompt)
        response = self.yandex.translate(llm_response.content, "Tatar")
        return response.result
    
    def generate_happy_contin(self, words: List[str], prev_story: str) -> str:
        prompt = f"Write a super super short positive continuation of the story without an ending, using the words: {words} \
        The beginning of the story: {prev_story}"
        llm_response = self.llm.invoke(prompt)
        response = self.yandex.translate(llm_response.content, "Tatar")
        return response.result
    
    def generate_sad_end(self, words: List[str], prev_story: str) -> str:
        prompt = f"Write a super super short sad ending to the story using the words: {words} \
        The beginning of the story: {prev_story}"
        llm_response = self.llm.invoke(prompt)
        response = self.yandex.translate(llm_response.content, "Tatar")
        return response.result
    
    def generate_happy_end(self, words: List[str], prev_story: str) -> str:
        prompt = f"Write a super super short happy ending to the story using the words: {words} \
        The beginning of the story: {prev_story}"
        llm_response = self.llm.invoke(prompt)
        response = self.yandex.translate(llm_response.content, "Tatar")
        return response.result
    
    def take_first_sent(self, text: str) -> str:
        sentences = nltk.sent_tokenize(text)
        first_two_sentences = ' '.join(sentences[:2])
        first_two_sentences = first_two_sentences.strip()
        return first_two_sentences
    
    def chat(self, text: str) -> str:
        message = self.yandex.translate(text, "English")
        llm_response = self.llm.invoke(message.result)
        response = self.yandex.translate(llm_response.content, "Tatar")
        return response.result
    
    def translate_words(self, words: List[str]) -> str:
        new_words = []
        for word in words:
            new_word = self.yandex.translate(word, "English")
            new_words.append(new_word.result)
        return new_words

    def get_feedback(self, str_len: int) -> str:
        feedbacks = {1: "В целом, ваше эссе хорошо структурировано и содержит некоторые интересные аргументы.\
        Однако, я заметил, что некоторые из ваших предложений недостаточно четкие и могут быть неправильно истолкованы.\
        Постарайтесь в следующий раз использовать более точные и конкретные формулировки.", 
        2: "Хотя вы привели некоторые примеры для поддержки ваших аргументов, я думаю, \
        что вам стоит найти еще несколько более убедительных доказательств. \
        В следующий раз постарайтесь провести более тщательное исследование и найти более качественные источники.",
        3: "Я заметил, что в некоторых местах ваше эссе недостаточно связано и переходит \
        от одной темы к другой без явного перехода. Постарайтесь в следующий раз использовать более четкие переходные \
        предложения и связать свои аргументы более тесно.",
        4: "Ваше эссе содержит некоторые грамматические и орфографические ошибки, которые могут отвлекать читателя и портить общее впечатление. \
        Постарайтесь в следующий раз более тщательно проверить свою работу на предмет ошибок и, возможно, попросить кого-нибудь еще проверить ее",
        5: "В вашем эссе явно прослеживается глубокое понимание темы и хорошая аргументация.\
        Вы умело используете примеры и факты, чтобы подкрепить свою точку зрения, и делаете это таким образом, чтобы читатель мог \
        легко последовать за вашей логикой. Кроме того, вы смогли найти новый взгляд на эту тему, который необычен и заставляет задуматься.",
        6: "Ваше эссе отличается ясностью и структурной целостностью. Вы четко формулируете свои мысли и последовательно развиваете свою аргументацию, \
        что делает ваше эссе легким для чтения и понимания. Кроме того, вы смогли уложиться в заданный объем, не жертвуя при \
        этом качеством и содержанием своего эссе.",
        7: "В вашем эссе есть несколько очень удачных формулировок и выражений, которые заставляют читателя задуматься и делают \
        ваше эссе более интересным и привлекательным. Вы умело используете язык, чтобы передать свою точку зрения \
        и заинтересовать читателя, и это видно в каждом предложении вашего эссе.",
        8: "В целом, ваше эссе производит впечатление хорошо продуманного и аргументированного текста. Вы явно потратили время и усилия, чтобы написать его, и это видно \
        в каждом аспекте вашего эссе. Вы смогли найти новый взгляд на эту тему, уложиться в заданный объем, сохранив при этом качество и содержание, \
        и написать текст, который легко читать и понимать. Отличная работа!"
        }
        if str_len < 150:
            return feedbacks[2]
        else:
            return feedbacks[randint(8)]

