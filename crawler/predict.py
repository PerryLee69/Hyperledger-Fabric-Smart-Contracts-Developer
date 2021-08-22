import tensorflow as tf
from transformers import GPT2Config, TFGPT2LMHeadModel, GPT2Tokenizer
import sys

output_dir = 'D:/graduation/crawler/model_custom/'
tokenizer = GPT2Tokenizer.from_pretrained(output_dir)
model = TFGPT2LMHeadModel.from_pretrained(output_dir)

text = sys.argv[0]

# encoding the input text
input_ids = tokenizer.encode(text, return_tensors='tf')

# getting out output
beam_output = model.generate(
  input_ids,
  max_length = 2,
  num_beams = 5,
  temperature = 0.7,
  no_repeat_ngram_size=2,
  num_return_sequences=1
)

# print predictions
# print(tokenizer.decode(beam_output[0]))

# coding=UTF-8
filename = 'D:\\graduation\\crawler\\results.txt'
with open(filename, 'w') as file_object:
  for output in beam_output:
    file_object.write(tokenizer.decode(output)+'\n')