package com.example.evenassignment.base.screens.ui.dashboard

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.evenassignment.base.screens.ServiceTypeActivity
import com.example.evenassignment.databinding.FragmentDashboardBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

//        val textView: TextView = binding.textDashboard
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

        val translationAnimator = ObjectAnimator.ofFloat(
            binding.plusIcon, "translationY", 300f
        ).apply {
            duration = 750
        }
        translationAnimator.start()

        val alphaAnimator = ObjectAnimator.ofFloat(
            binding.plusIcon, "alpha", 0.0f, 1.0f
        )
    }

    private fun initUi() {
        binding.plusIcon.setOnClickListener {
            startActivity(Intent(activity, ServiceTypeActivity::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: called")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: called")

        val translationAnimator = ObjectAnimator.ofFloat(
            binding.plusIcon, "translationY", 0.0f
        )

        val alphaAnimator = ObjectAnimator.ofFloat(
            binding.plusIcon, "alpha", 1.0f, 0.0f
        )

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationAnimator, alphaAnimator)
        animatorSet.duration = 1000
        animatorSet.start()

        viewLifecycleOwner.lifecycleScope.launch {
            delay(animatorSet.duration)
        }
//        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: called")
    }
}