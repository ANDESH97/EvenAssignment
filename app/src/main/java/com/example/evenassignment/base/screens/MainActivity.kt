package com.example.evenassignment.base.screens

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.evenassignment.R
import com.example.evenassignment.base.screens.ui.dashboard.DashboardFragment
import com.example.evenassignment.databinding.ActivityMainBinding
import com.simform.custombottomnavigation.Model
import com.simform.custombottomnavigation.SSCustomBottomNavigation
import kotlin.math.hypot

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headingText.alpha = 0.0f
        binding.addConsultation.alpha = 0.0f

        binding.addIcon.setOnClickListener {
            startServiceLayoutAnimation()
        }

        binding.closeServiceLayout.setOnClickListener {
            closeServiceLayout()
        }

        setBottomNavigationWithNavController(savedInstanceState)

//        val navView: BottomNavigationView = binding.navView

//        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    private fun setBottomNavigationWithNavController(savedInstanceState: Bundle?) {

        val activeIndex = savedInstanceState?.getInt("activeIndex") ?: 0

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val menuItems = arrayOf(
            Model(
                icon = R.drawable.ic_home_black_24dp,                // Icon
                destinationId = R.id.navigation_home,     // destinationID
                id = 0,                // ID
                text = R.string.empty_string_text,               // Icon with Text, If you don't want text then don't pass it
            ),
            Model(
                icon = R.drawable.ic_dashboard_black_24dp,
                destinationId = R.id.navigation_dashboard,
                id = 1,
                text = R.string.empty_string_text
            ),
            Model(
                R.drawable.ic_notifications_black_24dp,
                R.id.navigation_notifications,
                2,
                R.string.empty_string_text,
            )
        )

        binding.navView.apply {
            setMenuItems(menuItems, activeIndex)
            setupWithNavController(navController)
        }


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d(TAG, "on destination changed: " + destination.displayName)

            if(destination.displayName.contains("navigation_dashboard")) {
                revealDashBoardElements()
            } else {
                hideDashBoardElements()
            }
        }
    }

    private fun revealDashBoardElements() {
        binding.addIcon.alpha = 1.0f

        val alphaRevealAnimator = ObjectAnimator.ofFloat(
            binding.headingText, "alpha", 0.0f, 1.0f
        )

        val addConsultationRevealAnimation = ObjectAnimator.ofFloat(
            binding.addConsultation, "alpha", 0.0f, 1.0f
        )

        val translationAddIconAnimator = ObjectAnimator.ofFloat(
            binding.addIcon, "translationY", 300f
        )

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            alphaRevealAnimator,
            addConsultationRevealAnimation,
            translationAddIconAnimator
        )

        animatorSet.duration = 500
        animatorSet.start()
    }

    private fun hideDashBoardElements() {

        if(binding.headingText.alpha != 0.0f && binding.addConsultation.alpha != 0.0f) {
            val alphaHideAnimator = ObjectAnimator.ofFloat(
                binding.headingText, "alpha", 1.0f, 0.0f
            )

            val addConsultationHideAnimation = ObjectAnimator.ofFloat(
                binding.addConsultation, "alpha", 1.0f, 0.0f
            )

            val hideAddIconAnimator = ObjectAnimator.ofFloat(
                binding.addIcon, "alpha", 1.0f, 0.0f
            )

            val translationAddIconYAnimator = ObjectAnimator.ofFloat(
                binding.addIcon, "translationY", -100f
            )

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(
                alphaHideAnimator,
                addConsultationHideAnimation,
                hideAddIconAnimator,
                translationAddIconYAnimator
            )

            animatorSet.duration = 500
            animatorSet.start()
        }
    }

    private fun startServiceLayoutAnimation() {

        hideDashBoardElements()

        val dashboardFragmentView = findViewById<View>(R.id.dashboard_screen)
        dashboardFragmentView?.visibility = View.INVISIBLE

        val rotateAnimator = ObjectAnimator.ofFloat(
            binding.addIcon, "rotation", 0f, -90f
        )

        val translationXAnimator = ObjectAnimator.ofFloat(
            binding.addIcon, "translationX", -50f
        )

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(rotateAnimator, translationXAnimator)
        animatorSet.duration = 250

        val circularReveal = ViewAnimationUtils.createCircularReveal(
            binding.shape,
            (binding.addIcon.left + binding.addIcon.right)/2,
            (binding.addIcon.top + binding.addIcon.bottom)/2,
            (binding.addIcon.height/2).toFloat(), hypot(binding.root.width.toDouble(), binding.root.height.toDouble()).toFloat()
        )

        circularReveal.interpolator = AccelerateDecelerateInterpolator()

        circularReveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                binding.shape.visibility = View.INVISIBLE
                binding.navView.visibility = View.INVISIBLE

                binding.serviceTypeLayout.visibility = View.VISIBLE
            }
        })

        binding.shape.visibility = View.VISIBLE
        // Finally start the animation
        animatorSet.start().also {
            circularReveal.start()
        }
    }

    private fun closeServiceLayout() {

        val translationXAnimator = ObjectAnimator.ofFloat(
            binding.closeServiceLayout, "translationX", 100f
        )

        val translationYAnimator = ObjectAnimator.ofFloat(
            binding.closeServiceLayout, "translationY", 100f
        )

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationXAnimator, translationYAnimator)
        animatorSet.duration = 500

        val circularReveal = ViewAnimationUtils.createCircularReveal(
            binding.closeServiceLayout,
            (binding.closeServiceLayout.width)/2,
            (binding.closeServiceLayout.height)/2,
            hypot(binding.root.width.toDouble(), binding.root.height.toDouble()).toFloat(),
            (binding.addIcon.height/2).toFloat()
        )

        circularReveal.interpolator = AccelerateDecelerateInterpolator()

        circularReveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                binding.shape.visibility = View.INVISIBLE
                binding.navView.visibility = View.VISIBLE

                binding.serviceTypeLayout.visibility = View.INVISIBLE
            }
        })

        // Finally start the animation
        animatorSet.start().also {
            circularReveal.start()
            val translatePlusIconXAnimator = ObjectAnimator.ofFloat(
                binding.addIcon, "translationX", 0f
            )

            val translatePlusIconYAnimator = ObjectAnimator.ofFloat(
                binding.addIcon, "translationY", 300f
            )

            val plusIconAnimatorSet = AnimatorSet()
            plusIconAnimatorSet.playTogether(translatePlusIconXAnimator, translatePlusIconYAnimator)
            plusIconAnimatorSet.duration = 250
            plusIconAnimatorSet.start()
        }

        val dashboardFragmentView = findViewById<View>(R.id.dashboard_screen)
        dashboardFragmentView?.visibility = View.VISIBLE

        binding.addIcon.alpha = 1.0f
        binding.headingText.alpha = 1.0f
        binding.addConsultation.alpha = 1.0f
    }
}