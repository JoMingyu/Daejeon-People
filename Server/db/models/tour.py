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


class TourBase(TourModel):
    credit_card = StringField()
    baby_carriage = StringField()
    pet = StringField()
    info_center = StringField()
    use_time = StringField()
    rest_date = StringField()


class TouristAttractionModel(TourBase):
    # content type id 12
    pass


class CulturalFacilityModel(TourBase):
    # content type id 14
    use_fee = StringField()
    spend_time = StringField()


class FestivalModel(TourModel):
    # content type id 15
    start_date = StringField()
    end_date = StringField()
    use_fee = StringField()
    spend_time = StringField()
    place = StringField()


class TourCourseModel(TourModel):
    # content type id 25
    spend_time = StringField()
    distance = StringField()


class LeisureModel(TourBase):
    # content type id 28
    use_fee = StringField()


class AccommodationModel(TourModel):
    # content type id 32
    info_center = StringField()
    checkin_time = StringField()
    checkout_time = StringField()
    benikia = BooleanField()
    goodstay = BooleanField()
    capacity = StringField()


class ShoppingModel(TourBase):
    # content type id 38
    pass


class RestaurantModel(TourModel):
    # content type id 39
    credit_card = StringField()
    info_center = StringField()
    use_time = StringField()
    rest_date = StringField()
    rep_menu = StringField()
