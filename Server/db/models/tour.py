from mongoengine import *

from db.models.tour_base import TourTopModel, TourBase, top_insert, base_insert


class TouristAttractionModel(TourBase):
    # content type id 12
    @classmethod
    def insert(cls, tour, detail_images):
        base_insert(cls, tour, detail_images).save()


class CulturalFacilityModel(TourBase):
    # content type id 14
    use_fee = StringField()
    spend_time = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        _cls = base_insert(cls, tour, detail_images)
        _cls.use_fee = tour['use_fee']
        _cls.spend_time = tour['spend_time']

        _cls.save()


class FestivalModel(TourTopModel):
    # content type id 15
    start_date = StringField()
    end_date = StringField()
    use_fee = StringField()
    spend_time = StringField()
    place = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        _cls = top_insert(cls, tour, detail_images)
        _cls.start_date = str(tour['start_date'])
        _cls.end_date = str(tour['end_date'])
        _cls.use_fee = tour['use_fee']
        _cls.spend_time = tour['spend_time']
        _cls.place = tour['place']

        _cls.save()


class TourCourseModel(TourTopModel):
    # content type id 25
    spend_time = StringField()
    distance = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        _cls = top_insert(cls, tour, detail_images)
        _cls.spend_time = tour['spend_time']
        _cls.distance = tour['distance']

        _cls.save()


class LeisureModel(TourBase):
    # content type id 28
    use_fee = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        _cls = base_insert(cls, tour, detail_images)
        _cls.use_fee = tour['use_fee']

        _cls.save()


class AccommodationModel(TourTopModel):
    # content type id 32
    checkin_time = StringField()
    checkout_time = StringField()
    benikia = BooleanField()
    goodstay = BooleanField()
    capacity = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        _cls = top_insert(cls, tour, detail_images)
        _cls.checkin_time = tour['checkin_time']
        _cls.checkout_time = tour['checkout_time']
        _cls.benikia = tour['benikia']
        _cls.goodstay = tour['goodstay']
        _cls.capacity = tour['capacity']

        _cls.save()


class ShoppingModel(TourBase):
    # content type id 38
    @classmethod
    def insert(cls, tour, detail_images):
        base_insert(cls, tour, detail_images).save()


class RestaurantModel(TourTopModel):
    # content type id 39
    credit_card = StringField()
    open_time = StringField()
    rest_date = StringField()
    rep_menu = StringField()

    @classmethod
    def insert(cls, tour, detail_images):
        _cls = top_insert(cls, tour, detail_images)
        _cls.credit_card = tour['credit_card']
        _cls.open_time = tour['open_time']
        _cls.rest_date = tour['rest_date']
        _cls.rep_menu = tour['rep_menu']

        _cls.save()
