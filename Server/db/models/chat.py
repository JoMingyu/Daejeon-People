from db.mongo import *
from db.models.user import AccountModel
from db.models.tour import TourModel
from datetime import datetime


class MessageModel(EmbeddedDocument):
    # Identify : idx
    idx = IntField(primary_key=True, min_value=1)
    sender = ReferenceField(AccountModel, required=True)
    send_time = StringField(required=True, default=str(datetime.today())[:-7])
    remaining_views = IntField(required=True)

    meta = {'allow_inheritance': True}


class TextMessageModel(MessageModel):
    content = StringField(required=True)


class ImageMessageModel(MessageModel):
    image_url = StringField(required=True)


class PinMessageModel(MessageModel):
    pin = ReferenceField(TourModel, required=True)


class ChatModel(Document):
    topic = StringField(primary_key=True)
    title = StringField(required=True)
    messages = ListField(EmbeddedDocumentField(MessageModel))
