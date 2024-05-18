import nltk
from typing import Dict, Any, List
from translatepy import Translator
from translatepy.translators.yandex import YandexTranslate

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
        prompt = f"Напиши завязку истории на татарском языке. \
            В истории обязательно должны быть слова: {words}."
        llm_response = self.llm.invoke(prompt)
        return llm_response.content
    
    def generate_sad_contin(self, words: List[str], prev_story: str) -> str:
        prompt = f"Сделай грустное продолжение истории без концовки, используя слова: {words} \
            начало истории: {prev_story}"
        llm_response = self.llm.invoke(prompt)
        return llm_response.content
    
    def generate_happy_contin(self, words: List[str], prev_story: str) -> str:
        prompt = f"Сделай позитивное продолжение истории без концовки, используя слова: {words} \
            начало истории: {prev_story}"
        llm_response = self.llm.invoke(prompt)
        return llm_response.content
    
    def generate_sad_end(self, words: List[str], prev_story: str) -> str:
        prompt = f"Сделай грустную концовку истории, используя слова: {words} \
            начало истории: {prev_story}"
        llm_response = self.llm.invoke(prompt)
        return llm_response.content
    
    def generate_happy_end(self, words: List[str], prev_story: str) -> str:
        prompt = f"Сделай счастливую концовку истории, используя слова: {words} \
            начало истории: {prev_story}"
        llm_response = self.llm.invoke(prompt)
        return llm_response.content
    
    def take_first_sent(self, text: str) -> str:
        sentences = nltk.sent_tokenize(text)
        first_two_sentences = ' '.join(sentences[:2])
        first_two_sentences = first_two_sentences.strip()
        return first_two_sentences
    
    def chat(self, text: str) -> str:
        message = self.yandex.translate(text, "Russian")
        llm_response = self.llm.invoke(message.result)
        response = self.yandex.translate(llm_response.content, "Tatar")
        return response.result
