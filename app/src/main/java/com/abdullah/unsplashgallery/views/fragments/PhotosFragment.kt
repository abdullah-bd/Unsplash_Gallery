package com.abdullah.unsplashgallery.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullah.unsplashgallery.MyApplication
import com.abdullah.unsplashgallery.adapters.PhotosAdapter
import com.abdullah.unsplashgallery.databinding.FragmentPhotosBinding
import com.abdullah.unsplashgallery.models.ImagesResponseItem
import com.abdullah.unsplashgallery.utils.Constants
import com.abdullah.unsplashgallery.utils.ProgressUtils
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class PhotosFragment : Fragment() {

    private lateinit var binding: FragmentPhotosBinding
    var page = 1

    @Inject
    lateinit var viewModel: PhotosViewModel

    var adapter: PhotosAdapter? = null
    lateinit var list: List<ImagesResponseItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.iv.visibility == View.VISIBLE) {
                    binding.rvImages.visibility = View.VISIBLE

                    binding.iv.visibility = View.GONE

                } else
                    requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, onBackPressedCallback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        loadData(page)
        observers()
        listeners()

    }


    private fun listeners() {
        binding.rvImages.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null) {
                    Log.d(
                        "onScrollStateChanged",
                        "onScrollStateChanged: " + linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    )
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1 >= page * Constants.PAGE_ITEM) {
                        loadData(++page)

                    }
                }
            }
        })
    }


    private fun loadData(page: Int) {
        viewModel.getPhotos(page)
    }

    private fun observers() {

        viewModel.items.observe(viewLifecycleOwner) {
            if (it != null) {
                if (adapter == null) {
                    list = it
                    initRecycleView()
                } else {
                    list = it
                    adapter!!.notifyItemRangeChanged((page - 1) * 10, page * 10)
                }
            }
        }

        viewModel.eventShowMessage.observe(viewLifecycleOwner) {

            it?.run {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    this,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("OK") { }
                    .apply { show() }
            }
        }
        viewModel.eventShowLoading.observe(viewLifecycleOwner) {

            it?.run {
                if (this) {
                    ProgressUtils.showProgress(requireActivity())
                } else {
                    ProgressUtils.hideProgress(requireActivity())
                }
            }
        }
    }

    private fun initRecycleView() {
        binding.rvImages.layoutManager = GridLayoutManager(activity, 2)
        adapter = PhotosAdapter(this)
        binding.rvImages.adapter = adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).mInject.inject(this)
    }

    fun loadImage(image: String) {
        binding.rvImages.visibility = View.GONE
        binding.iv.visibility = View.VISIBLE
        Glide.with(requireActivity())
            .load(image)
            .into(binding.iv)

    }


}