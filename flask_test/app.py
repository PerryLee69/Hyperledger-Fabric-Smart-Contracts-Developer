import json
from flask import Flask, request, jsonify
from transformers import TFGPT2LMHeadModel, GPT2Tokenizer
import time

# create flask app
app = Flask(__name__)

model_dir = '.\\static\\model_custom\\'
tokenizer = GPT2Tokenizer.from_pretrained(model_dir)
model = TFGPT2LMHeadModel.from_pretrained(model_dir)


# create interface
@app.route('/plugin', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        data = request.get_data()
        json_data = json.loads(data.decode('utf-8'))
        text = json_data.get('text')
    else:
        text = request.args.get('text')

    # encoding the input text
    input_ids = tokenizer.encode(text, return_tensors='tf')

    # print(tf.shape(input_ids))

    start_time = time.time()

    # getting out output
    beam_output = model.generate(
        input_ids,
        max_length=5,  # 输出选项长度
        num_beams=5,  #
        temperature=0.7,  #
        no_repeat_ngram_size=2,  #
        num_return_sequences=1  # 结果集内选项个数，相当于topk
    )

    end_time = time.time()
    print(end_time - start_time)

    return tokenizer.decode(beam_output[0]) + ',' + tokenizer.decode(beam_output[1]) + ',' + \
           tokenizer.decode(beam_output[2]) + ',' + tokenizer.decode(beam_output[3]) + ',' + \
           tokenizer.decode(beam_output[4])
    # return tokenizer.decode(beam_output[0])


# activate service
if __name__ == "__main__":
    app.run(host='127.0.0.1', port=8888)
