from tokeniser import BPE_token
from pathlib import Path
import os


# the folder 'data' contains all the files
paths = [str(x) for x in Path("./data/").glob("**/*.js")]
tokenizer = BPE_token()

# train the tokenizer model
tokenizer.bpe_train(paths)

# saving the tokenized data in our specified folder 
save_path = 'tokenized_data'  #directory name
tokenizer.save_tokenizer(save_path)

#init model

import tensorflow as tf
from transformers import GPT2Config, TFGPT2LMHeadModel, GPT2Tokenizer


# loading tokenizer from the saved model path
tokenizer = GPT2Tokenizer.from_pretrained(save_path)
tokenizer.add_special_tokens({
  "eos_token": "</s>",
  "bos_token": "<s>",
  "unk_token": "<unk>",
  "pad_token": "<pad>",
  "mask_token": "<mask>"
})

# creating the configurations from which the model can be made
config = GPT2Config(
  vocab_size=tokenizer.vocab_size,
  bos_token_id=tokenizer.bos_token_id,
  eos_token_id=tokenizer.eos_token_id
)

# creating the model
model = TFGPT2LMHeadModel(config)

#prepare data
single_string = ''
for filename in paths:
    with open(filename, "r", encoding='utf-8') as f:
        x = f.read()
    single_string += x + tokenizer.eos_token

#use tokenizer to encode to get sequence of token id
string_tokenized = tokenizer.encode(single_string)

examples = []
block_size = 100
BATCH_SIZE = 12
BUFFER_SIZE = 1000

#slice sequence in terms of attributes above to get examples
for i in range(0, len(string_tokenized) - block_size + 1, block_size):
    examples.append(string_tokenized[i:i + block_size])

inputs, labels = [], []

for ex in examples:
    inputs.append(ex[:-1])
    labels.append(ex[1:])

dataset = tf.data.Dataset.from_tensor_slices((inputs, labels))
dataset = dataset.shuffle(BUFFER_SIZE).batch(BATCH_SIZE, drop_remainder=True)