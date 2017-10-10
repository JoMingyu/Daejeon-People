from db.mongo import *


class TourModel(Document):
    content_id = IntField(primary_key=True)
    content_type_id = IntField(required=True)
    title = StringField()
    address = StringField()
    x = FloatField()
    y = FloatField()

    main_category = StringField()
    middle_category = StringField()
    small_category = StringField()
    tel_owner = StringField()
    tel = StringField()
    img_big_url = StringField()
    img_small_url = StringField()

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
    @classmethod
    def insert(cls, tour, detail_images):
        cls(
            content_id=tour['content_id'],
            content_type_id=tour['content_type_id'],
            title=tour['title'],
            address=tour['address'],
            x=tour['x'],
            y=tour['y'],
            main_category=tour['main_category'],
            middle_category=tour['middle_category'],
            small_category=tour['small_category'],
            tel_owner=tour['tel_owner'],
            tel=tour['tel'],
            img_big_url=tour['img_big_url'],
            img_small_url=tour['img_small_url'],
            views=tour['views'],
            images=detail_images,
            credit_card=tour['credit_card'],
            baby_carriage=tour['baby_carriage'],
            pet=tour['pet'],
            info_center=tour['info_center'],
            use_time=tour['use_time'],
            rest_date=tour['rest_date']).save()


class CulturalFacilityModel(TourBase):
    # content type id 14
    use_fee = StringField()
    spend_time = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        cls(

        ).save()


class FestivalModel(TourModel):
    # content type id 15
    start_date = StringField()
    end_date = StringField()
    use_fee = StringField()
    spend_time = StringField()
    place = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        cls(

        ).save()


class TourCourseModel(TourModel):
    # content type id 25
    spend_time = StringField()
    distance = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        cls(

        ).save()


class LeisureModel(TourBase):
    # content type id 28
    use_fee = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        cls(

        ).save()


class AccommodationModel(TourModel):
    # content type id 32
    info_center = StringField()
    checkin_time = StringField()
    checkout_time = StringField()
    benikia = BooleanField()
    goodstay = BooleanField()
    capacity = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        cls(

        ).save()


class ShoppingModel(TourBase):
    # content type id 38
    @classmethod
    def insert(cls, tour, detail_images):
        cls(

        ).save()


class RestaurantModel(TourModel):
    # content type id 39
    credit_card = StringField()
    info_center = StringField()
    use_time = StringField()
    rest_date = StringField()
    rep_menu = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        cls(

        ).save()
