
function loadSliderStyles() {
    $('.testRange').each(function () {
        var min = $(this).find('span.min').data('min');
        var max = $(this).find('span.max').data('max');
        var result = $(this).find('div.testRangeInner').data('result');
        var slider_range = $(this).find('p');
        var total_width = $(this).width();
        var slider_range_width = slider_range.width();
        var slider_range_percentage = (slider_range_width / 100) * total_width;
        var percentage = (result / total_width) * 100;
        var max_side = -16;
        var position = percentage;

        if (result > max) {

            // ===================================================
            // It's right mathematic calculation, but looks not
            // good on the page.
            // ===================================================

            //  if (-position < max_side)
            //      slider_range.css({ right: max_side + "%" });
            //  else
            //      slider_range.css({ right: -position + "%" });


            slider_range.css({ right: "-11%", width: "62px", "text-align": "left" });
            slider_range.css("min-width", "36px");
            slider_range.addClass('OutOfRangeRight');
        }
        else if (result < min) {
            // ===================================================
            // It's right mathematic calculation, but looks not
            // good on the page.
            // ===================================================
            //            if (-position < max_side)
            //                slider_range.css({ left: max_side + "%" });
            //            else
            //                slider_range.css({ left: -position + "%" });
            slider_range.css({ left: "-9.5%", width: "62px", "text-align": "right" });
            slider_range.css("min-width", "36px");
            slider_range.addClass('OutOfRangeLeft');
            if (!$(slider_range).children().first().hasClass('RightLimiter')) {
                LeftLimiter = $(this).find('span.LeftLimiter');
                RightLimiter = $(this).find('span.RightLimiter');
                LeftLimiter.removeClass('LeftLimiter').addClass('RightLimiter');
                RightLimiter.removeClass('RightLimiter').addClass('LeftLimiter');
            }
        }
        else {

            var box_half_width = (slider_range_width / 2) + 4;
            var a = 118 / (max - min);
            var b = (result - min) * a;
            b = b + 47 - box_half_width;

            if (result < min)
                slider_range.css({ left: -1 + "px" });
            else if (result > max)
                slider_range.css({ right: -1 + "%" });
            else
                slider_range.css({ left: b + "px" });

            slider_range.addClass('WithinRange');

        }

    });
}