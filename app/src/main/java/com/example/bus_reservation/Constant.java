package com.example.bus_reservation;

public class Constant {

    public static final String Base_url_stripe = "http://marketist.eparking.website/Freelancer/provider_stripe.php?";

    public static final String Base_url="http://marketist.eparking.website/Freelancer/Api.php";

    public static final String Base_url_booking_accept=Base_url+"?accept_booking=true&booking_id=";

    public static final String Base_url_booking_delete=Base_url+"?cancel_booking=true&booking_id=";

    public static final String Base_url_booking_view=Base_url+"?view_booking=true&booking_id=";

    public static final String booking_image_url="http://marketist.eparking.website/uploads/booking/";

    public static final String Default_membership="http://marketist.eparking.website/Freelancer/Api.php?payments_settings=true";

    public static final String Base_url_delete_gallery = Base_url+"?remove_gallery=true&image_id=";

    public static final String Base_url_delete_service = Base_url+"?remove_service=true&service_id=";

    public static final String Base_url_membership=Base_url+"?memberships=true";

    public static final String Base_url_get_services = Base_url+"?get_services=true&provider_id=";

    public static final String Base_url_get_category = Base_url+"?categories=true";

    public static final String Base_url_public_gallery = Base_url+"?get_publicImages=true&provider_id=";

    public static final String Base_url_private_gallery = Base_url+"?get_privateImages=true&provider_id=";

    public static final String Base_url_bookings=Base_url+"?booking_list=true&provider_id=";

    public static final String Image_url="http://marketist.eparking.website/Client/";

    public static final String gallery_url="http://marketist.eparking.website/Freelancer/";

    public static final String Banner_url = Base_url+"?slider=true";

    public static final String Base_url_Get_Data = Base_url+"?profile_data=true&provider_id=";

    public static final String Base_url_Create_Booking = Base_url+"/createBooking";

}
