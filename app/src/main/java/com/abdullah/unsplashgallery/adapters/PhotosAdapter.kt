package com.abdullah.unsplashgallery.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.abdullah.unsplashgallery.databinding.ItemPhotoBinding
import com.abdullah.unsplashgallery.models.ImagesResponseItem
import com.abdullah.unsplashgallery.views.fragments.PhotosFragment
import com.bumptech.glide.Glide

/**
 * Created by "Md. Abdullah" on 06,September,2022
 * Developer email: "mohammad.abdullah.raj.bd@gmail.com"
 * Github: "abdullah-bd"
 */
class PhotosAdapter(private val fragment: PhotosFragment) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosAdapter.ViewHolder {

        return ViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var lastPosition = -1

    override fun onBindViewHolder(holder: PhotosAdapter.ViewHolder, position: Int) {
        val item = fragment.list[position]

        Glide.with(holder.binding.tvName.context)
            .load(item.urls.regular)
            .into(holder.binding.ivIcon)

        holder.binding.btParent.setOnClickListener {
            fragment.loadImage(item.urls.regular)
        }

        setAnimation(holder.itemView, position)

        holder.setItems(item)


    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 1000
        view.startAnimation(anim)
    }

    private fun setScaleAnimation(view: View) {
        val anim = ScaleAnimation(
            0.0f,
            1.0f,
            0.0f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.duration = 1000
        view.startAnimation(anim)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {

        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val anim = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            anim.duration = 500 //to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim)
            lastPosition = position
        }
    }

    override fun getItemCount() = fragment.list.size


    inner class ViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItems(item: ImagesResponseItem) {
            binding.item = item
        }

    }
}