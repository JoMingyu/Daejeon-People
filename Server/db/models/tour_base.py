from db.mongo import *


def top_insert(cls, tour, detail_images):
    """
    Initialize TourTopModel fields

    :param cls: Child class from TourTopModel
    :param tour: Tour list
    :param detail_images: Image url list

    :type tour: dict
    :type detail_images: list

    :return: cls
    """
    # cls를 그대로 받아와서 최상위 모델인 TourTopModel에 해당하는 인스턴스화 진행
    return cls(
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
        images=detail_images)


def base_insert(cls, tour, detail_images):
    """
    Initialize TourBase fields

    :param cls: Child class from TourBase
    :param tour: Tour list
    :param detail_images: Image url list

    :type tour: dict
    :type detail_images: list

    :return: cls
    """
    # TourBase에 해당하는 인스턴스화 진행
    cls = top_insert(cls, tour, detail_images)

    cls.credit_card = tour['credit_card']
    cls.baby_carriage = tour['baby_carriage']
    cls.pet = tour['pet']
    cls.use_time = tour['use_time']
    cls.rest_date = tour['rest_date']

    return cls


class TourTopModel(Document):
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


class TourBase(TourTopModel):
    credit_card = StringField()
    baby_carriage = StringField()
    pet = StringField()
    use_time = StringField()
    rest_date = StringField()
