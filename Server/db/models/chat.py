from db.mongo import *


class ChatModel(EmbeddedDocument):
    topic = StringField(primary_key=True)
    title = StringField(required=True)
