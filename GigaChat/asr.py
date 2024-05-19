
import requests
import Levenshtein

class ASR_class:

    def __init__(
        self
    ):
        self.url = 'https://rus-asr.api.translate.tatar/listening/'
        self.headers = {
            'accept': 'application/json',
        }
    def asr(self, filename):
        files = {
            'file':(filename, open(filename, 'rb'), 'audio/wav')
        }

        asr = requests.post(self.url, headers=self.headers, files=files)

        return asr
