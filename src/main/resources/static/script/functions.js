$(document).ready(function ($) {

    $("#locales").change(function () {
        var selectedOption = $('#locales').val();
        if (selectedOption != '') {
            window.location.replace('?lang=' + selectedOption);
        }
    });

    'use strict';
    //***************************
    // Sticky Header Function
    //***************************
    $(window).scroll(function () {
        if ($(this).scrollTop() > 170) {
            $('body').addClass("consultant-sticky");
        } else {
            $('body').removeClass("consultant-sticky");
        }
    });

    $('.select2').select2();
    $('.select2-tags').select2({tags: true});

    //***************************
    // BannerOne Functions
    //***************************
    $('.charity-banner-one').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        infinite: true,
        dots: false,
        arrows: false,
        fade: true,
        responsive: [
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                    infinite: true,
                }
            },
            {
                breakpoint: 800,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            },
            {
                breakpoint: 400,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            }
        ]
    });
    //***************************
    // banner-two Functions
    //***************************
    $('.charity-banner-two').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 10000,
        infinite: true,
        dots: false,
        prevArrow: "<span class='slick-arrow-left'><i class='fa fa-angle-right'></i></span>",
        nextArrow: "<span class='slick-arrow-right'><i class='fa fa-angle-right'></i></span>",
        responsive: [
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1,
                    infinite: true,
                }
            },
            {
                breakpoint: 800,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            },
            {
                breakpoint: 400,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            }
        ]
    });
    //***************************
    // PartnerSlider Functions
    //***************************
    $('.charity-partner-slider').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        infinite: true,
        dots: false,
        prevArrow: "<span class='slick-arrow-left'><i class='fa fa-angle-right'></i></span>",
        nextArrow: "<span class='slick-arrow-right'><i class='fa fa-angle-right'></i></span>",
        responsive: [
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 1,
                    infinite: true,
                }
            },
            {
                breakpoint: 800,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            },
            {
                breakpoint: 400,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            }
        ]
    });

    //***************************
    // Click to Top Button
    //***************************
    $('.charity-back-top').on("click", function () {
        $('html, body').animate({
            scrollTop: 0
        }, 800);
        return false;
    });
    //***************************
    // Parent AddClass Function
    //***************************
    $(".charity-dropdown-menu,.charity-megamenu").parent("li").addClass("subdropdown-addicon");

    //***************************
    // Fancybox Function
    //***************************
    $(".fancybox").fancybox({
        openEffect: 'elastic',
        closeEffect: 'elastic',
    });

    //***************************
    // Progressbar Function
    //***************************
    $('.progressbar1').progressBar({
        percentage: false,
        animation: true,
        backgroundColor: "#606060",
        barColor: "#edb542",
        height: "10",
    });

    //***************************
    // Counter Function
    //***************************
    // $('#word-count1').$SimpleCounter({
    //     end: 2210,
    //     duration: 40000
    // });
    // $('#word-count2').$SimpleCounter({
    //     end: 210,
    //     duration: 40000
    // });
    // $('#word-count3').$SimpleCounter({
    //     end: 957,
    //     duration: 40000
    // });
    // $('#word-count4').$SimpleCounter({
    //     end: 3010,
    //     duration: 40000
    // });

    //********************************
    // Switcher MainToggle Function
    //*******************************
    $(".switcher-style-btn").on("click", function () {
        $(".switcher-style").trigger('click')
        $(this).toggleClass('switcher-style-btn-open');
        $(".switcher-style").toggleClass('switcher-sidebar-on');
        return false;
    });

    //********************************
    // TopStrip OnOff Function
    //*******************************
    $(".topstrip-switch .switch").on("click", function () {
        $(".charity-main-wrapper").trigger('click')
        $(this).toggleClass('top-strip-switche');
        $(".charity-main-wrapper").toggleClass('top-strip-off');
        return false;
    });
    //********************************
    // Logo Background Function
    //*******************************
    $(".logobackground-switch .switch").on("click", function () {
        $(".charity-main-wrapper").trigger('click')
        $(this).toggleClass('logobackground-btn');
        $(".charity-main-wrapper").toggleClass('logobackground-off');
        return false;
    });
    //********************************
    // Logo Background Function
    //*******************************
    $(".bannerslider-switch .switch").on("click", function () {
        $(".charity-main-wrapper").trigger('click')
        $(this).toggleClass('bannerslider-btn');
        $(".charity-main-wrapper").toggleClass('bannerslider-on');
        return false;
    });
    //********************************
    // Header Border Function
    //*******************************
    $(".header-border .switch").on("click", function () {
        $(".charity-main-wrapper").trigger('click')
        $(this).toggleClass('header-border-btn');
        $(".charity-main-wrapper").toggleClass('header-border-off');
        return false;
    });

    //***************************
    // Countdown Function
    //***************************
    var austDay = new Date($('#charity-countdown').data('date'));
    $('#charity-countdown').countdown({
        until: austDay
    });
    $('#year').text(austDay.getFullYear());

    //***************************
    // Donation Active Function
    //***************************
    $('.charity-donation-section ul li').on('click', function () {
        $('li.current').removeClass('current');

        $(this).addClass('current');
        $("#donation-value").val($(this).data("value"));
    });
    $('.charity-donation-section ul li input[type=number]').on('change', function () {
        $("#donation-value").val($(this).val());
    });
});


//***************************
// FilterAble Function
//***************************
$(window).on('load', function () {
    var $grid = $('.charity-masonery-gallery').isotope({
        itemSelector: '.element-item',
        layoutMode: 'masonry'
    });
    // filter functions
    var filterFns = {
        // show if number is greater than 50
        numberGreaterThan50: function () {
            var number = $(this).find('.number').text();
            return parseInt(number, 10) > 50;
        },
        // show if name ends with -ium
        ium: function () {
            var name = $(this).find('.name').text();
            return name.match(/ium$/);
        }
    };
    // bind filter button click
    $('.charity-filterable').on('click', 'a', function () {
        var filterValue = $(this).attr('data-filter');
        // use filterFn if matches value
        filterValue = filterFns[filterValue] || filterValue;
        $grid.isotope({filter: filterValue});
    });
    // change is-checked class on buttons
    $('.charity-filterable').each(function (i, buttonGroup) {
        var $buttonGroup = $(buttonGroup);
        $buttonGroup.on('click', 'a', function () {
            $buttonGroup.find('.charity-active').removeClass('charity-active');
            $(this).addClass('charity-active');
        });
    });
});

//***************************
// Map Function
//***************************

function initMap() {
    // Basic options for a simple Google Map
    // For more options see: https://developers.google.com/maps/documentation/javascript/reference#MapOptions
    var mapOptions = {
        // How zoomed in you want the map to start at (always required)
        zoom: 11,

        // The latitude and longitude to center the map (always required)
        center: new google.maps.LatLng(40.6700, -73.9400), // New York

        // How you would like to style the map. 
        // This is where you would paste any style found on Snazzy Maps.
        styles: [{
            "featureType": "all",
            "elementType": "all",
            "stylers": [{"visibility": "on"}]
        }, {
            "featureType": "administrative",
            "elementType": "labels.text.fill",
            "stylers": [{"color": "#4e4841"}]
        }, {"featureType": "landscape", "elementType": "all", "stylers": [{"color": "#fbede2"}]}, {
            "featureType": "poi",
            "elementType": "all",
            "stylers": [{"visibility": "off"}]
        }, {
            "featureType": "road",
            "elementType": "all",
            "stylers": [{"saturation": -100}, {"lightness": 45}]
        }, {
            "featureType": "road.highway",
            "elementType": "all",
            "stylers": [{"visibility": "simplified"}]
        }, {
            "featureType": "road.arterial",
            "elementType": "all",
            "stylers": [{"hue": "#ff0000"}, {"visibility": "simplified"}]
        }, {
            "featureType": "road.arterial",
            "elementType": "labels.text",
            "stylers": [{"visibility": "on"}, {"hue": "#ff0000"}]
        }, {
            "featureType": "road.arterial",
            "elementType": "labels.icon",
            "stylers": [{"visibility": "off"}]
        }, {
            "featureType": "transit",
            "elementType": "all",
            "stylers": [{"visibility": "off"}]
        }, {"featureType": "water", "elementType": "all", "stylers": [{"color": "#febb64"}, {"visibility": "on"}]}]
    };

    // Get the HTML DOM element that will contain your map 
    // We are using a div with id="map" seen below in the <body>
    var mapElement = document.getElementById('map');

    // Create the Google Map using our element and options defined above
    var map = new google.maps.Map(mapElement, mapOptions);

    // Let's also add a marker while we're at it
    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(40.6700, -73.9400),
        map: map,
        title: 'Snazzy!'
    });
}