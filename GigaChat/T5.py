from transformers import AutoTokenizer
from transformers import AutoModelForSeq2SeqLM


class T5:
    def __init__(self, model_name, device='cuda'):
        self.tokenizer = AutoTokenizer.from_pretrained(model_name)
        self.model = AutoModelForSeq2SeqLM.from_pretrained(model_name).to(device)
        self.device = device


    def inference(self,essay):
        inputs = sself.tokenizer(essay, return_tensors="pt").input_ids
        outputs = self.model.generate(inputs.to(self.device), max_length=324, max_new_tokens=40, do_sample=True, top_k=30, top_p=0.95)
        return outputs
