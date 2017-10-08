from db.mongo import *
from datetime import date


class AccountModel(Document):
    id = StringField(required=True, primary_key=True)
    pw = StringField(required=True)

    registration_id = StringField(required=True, unique=True)
    email = StringField(unique=True)
    phone = StringField(unique=True)
    name = StringField(required=True)
    register_date = StringField(required=True, default=str(date.today()))

    friends = ListField()
    wish_list = ListField()


class CertifyModel(Document):
    user = ReferenceField(AccountModel, required=True)
    code = IntField(required=True)
