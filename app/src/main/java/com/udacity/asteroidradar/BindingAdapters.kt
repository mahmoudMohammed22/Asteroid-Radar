package com.udacity.asteroidradar


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.adapter.InfoPlantaryAdapter
import com.udacity.asteroidradar.main.AsteroidStatus
import com.udacity.asteroidradar.roomDatabase.AsteroidEntitry


@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous == true) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = imageView.resources.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = imageView.resources.getString(R.string.not_hazardous_asteroid_image)

    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = imageView.resources.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = imageView.resources.getString(R.string.potentially_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("pictureOfDay")
fun addPictureOfDay(imageView: ImageView,img:PictureOfDay?){
    img?.let {
        if (img.mediaType == "image"){
            Picasso.get().load(img.url).into(imageView)
            imageView.contentDescription = imageView.resources.getString(R.string.nasa_picture_of_day_content_description_format,img.title)
        }else{
            imageView.contentDescription = imageView.resources.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
        }
    }

}

@BindingAdapter("list_Info")
fun addInfoPlantaryDay(recyclerView: RecyclerView,data:List<AsteroidEntitry>?){
    val adapter = recyclerView.adapter as InfoPlantaryAdapter
    adapter.submitList(data)

}

@BindingAdapter("progress_bar")
fun downloadStatus(item :View,status:AsteroidStatus?){
    when(status){
        AsteroidStatus.LOADING->{
            item.visibility = View.VISIBLE
        }
        AsteroidStatus.ERROR->{
            item.visibility = View.GONE
        }
        AsteroidStatus.DONE->{
            item.visibility = View.GONE
        }

    }
}
