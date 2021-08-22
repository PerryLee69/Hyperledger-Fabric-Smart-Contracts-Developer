# defining our optimizer
optimizer = tf.keras.optimizers.Adam(learning_rate=3e-5, epsilon=1e-08, clipnorm=1.0)

# definining our loss function
loss = tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True)

# defining our metric which we want to observe
metric = tf.keras.metrics.SparseCategoricalAccuracy('accuracy')

# compiling the model
model.compile(optimizer=optimizer, loss=[loss, *[None] * model.config.n_layer], metrics=[metric])

num_epoch = 10
history = model.fit(dataset, epochs=num_epoch)