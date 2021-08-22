from transformers import WEIGHTS_NAME, CONFIG_NAME
import os
output_dir = './model_custom/'
# creating directory if it is not present
if not os.path.exists(output_dir):
  os.mkdir(output_dir)
model_to_save = model.module if hasattr(model, 'module') else model
output_model_file = os.path.join(output_dir, WEIGHTS_NAME)
output_config_file = os.path.join(output_dir, CONFIG_NAME)
# save model and model configs
model.save_pretrained(output_dir)
model_to_save.config.to_json_file(output_config_file)
# save tokenizer
tokenizer.save_pretrained(output_dir)