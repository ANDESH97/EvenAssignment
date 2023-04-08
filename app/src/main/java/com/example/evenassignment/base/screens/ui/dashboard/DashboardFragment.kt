package com.example.evenassignment.base.screens.ui.dashboard

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.evenassignment.R
import com.example.evenassignment.base.screens.ServiceTypeActivity
import com.example.evenassignment.databinding.FragmentDashboardBinding
import com.simform.custombottomnavigation.SSCustomBottomNavigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.hypot


class DashboardFragment : Fragment() {

    private val TAG = DashboardFragment::class.java.simpleName

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initUi()

//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onResume() {
        super.onResume()
        beginImageAnimation()
    }

    private fun beginImageAnimation() {

        val translationC1Animator = ObjectAnimator.ofFloat(
            binding.consultationOne, "translationX", 600f, 0f
        )

        val dateTimeOneAnimator = ObjectAnimator.ofFloat(
            binding.dateTimeOne, "translationX", 100f, 0f
        )

        val translationC2Animator = ObjectAnimator.ofFloat(
            binding.consultationTwo, "translationX", 600f, 0f
        )

        val dateTimeTwoAnimator = ObjectAnimator.ofFloat(
            binding.dateTimeTwo, "translationX", 100f, 0f
        )

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(translationC1Animator, dateTimeOneAnimator)
        animatorSet.duration = 200
        animatorSet.start()

        val animatorSetTwo = AnimatorSet()
        animatorSetTwo.playSequentially(translationC2Animator, dateTimeTwoAnimator)
        animatorSetTwo.duration = 200
        animatorSetTwo.start()
    }

    private fun initUi() {

//        binding.plusIcon.setOnClickListener {
//
//            Log.d(TAG, "initUi: clicked plus icon")
//            // Create a reveal {@link Animator} that starts clipping the view from
//            // the top left corner until the whole view is covered.
//
//            val rotateAnimator = ObjectAnimator.ofFloat(
//                binding.plusIcon, "rotation", 0f, -90f
//            )
//
//            val translationXAnimator = ObjectAnimator.ofFloat(
//                binding.plusIcon, "translationX", -200f
//            )
//
//            val animatorSet = AnimatorSet()
//            animatorSet.playTogether(rotateAnimator, translationXAnimator)
//            animatorSet.duration = 250
//
//            val circularReveal = ViewAnimationUtils.createCircularReveal(
//                binding.shape,
//                (binding.plusIcon.left + binding.plusIcon.right)/2,
//                (binding.plusIcon.top + binding.plusIcon.bottom)/2,
//                (binding.plusIcon.height/2).toFloat(), hypot(binding.root.width.toDouble(), binding.root.height.toDouble()).toFloat()
//            )
//
//            circularReveal.interpolator = AccelerateDecelerateInterpolator()
//
//            circularReveal.addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator) {
//                    binding.shape.visibility = View.INVISIBLE
//
//                    val bottomNavigationView = activity?.findViewById<SSCustomBottomNavigation>(R.id.nav_view)
//                    bottomNavigationView?.visibility = View.INVISIBLE
//
//                    binding.serviceTypeLayout.visibility = View.VISIBLE
//                }
//            })
//
//            binding.shape.visibility = View.VISIBLE
//            // Finally start the animation
//            animatorSet.start().also {
//                circularReveal.start()
//            }
//
//
//        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: called")

//        val translationAnimator = ObjectAnimator.ofFloat(
//            binding.plusIcon, "translationY", 0.0f
//        )
//
//        val alphaAnimator = ObjectAnimator.ofFloat(
//            binding.plusIcon, "alpha", 1.0f, 0.0f
//        )
//
//        val animatorSet = AnimatorSet()
//        animatorSet.playTogether(translationAnimator, alphaAnimator)
//        animatorSet.duration = 1000
//        animatorSet.start()
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            delay(animatorSet.duration)
//        }
//        _binding = null
    }
}