from db.mongo import *


class TourModel(Document):
    content_id = StringField(primary_key=True)
    content_type_id = StringField(required=True)
    title = StringField()
    address = StringField()
    zipcode = StringField()
    municipality = StringField()
    x = FloatField()
    y = FloatField()

    main_category = StringField()
    middle_category = StringField()
    small_category = StringField()
    tel_name = StringField()
    tel = StringField()
    img_big_url = StringField()
    img_small_url = StringField()
    homepage = StringField()
    overview = StringField()

    views = IntField(default=0)
    wish_count = IntField(required=True, default=0, min_value=0)
    images = ListField(StringField())

    meta = {'allow_inheritance': True}
