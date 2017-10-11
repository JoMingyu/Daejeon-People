import os
import time

from tourapi import TourAPI, AreaCodes

from db.models.tour import *


def parse():
    while True:
        TourTopModel.objects.delete()
        api = TourAPI(AreaCodes.DAEJEON, os.getenv('TOURAPI_KEY'), 'AND', 'Daejeon People')

        tour_list = api.get_tour_list()
        for tour in tour_list:
            detail_common = api.get_detail_common(tour['content_id'])
            detail_intro = api.get_detail_intro(tour['content_id'])
            detail_images = api.get_detail_images(tour['content_id'])
            detail_images = [_dict['origin'] for _dict in detail_images] if detail_images else []

            tour.update(detail_common)
            tour.update(detail_intro)

            cid = tour['content_type_id']
            print(tour['content_id'])
            if cid == 12:
                TouristAttractionModel.insert(tour, detail_images)
            elif cid == 14:
                CulturalFacilityModel.insert(tour, detail_images)
            elif cid == 15:
                FestivalModel.insert(tour, detail_images)
            elif cid == 25:
                TourCourseModel.insert(tour, detail_images)
            elif cid == 28:
                LeisureModel.insert(tour, detail_images)
            elif cid == 32:
                AccommodationModel.insert(tour, detail_images)
            elif cid == 38:
                ShoppingModel.insert(tour, detail_images)
            elif cid == 39:
                RestaurantModel.insert(tour, detail_images)

        time.sleep(60 * 60 * 48)

if __name__ == '__main__':
    parse()
