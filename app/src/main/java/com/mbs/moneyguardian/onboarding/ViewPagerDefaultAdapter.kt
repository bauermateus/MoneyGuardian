package com.mbs.moneyguardian.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mbs.moneyguardian.onboarding.fragments.ViewPager1Fragment
import com.mbs.moneyguardian.onboarding.fragments.ViewPager2Fragment
import com.mbs.moneyguardian.onboarding.fragments.ViewPager3Fragment

class ViewPagerDefaultAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                ViewPager1Fragment()
            }
            1->{
                ViewPager2Fragment()
            }
            2->{
                ViewPager3Fragment()
            }
            else->{
                Fragment()
            }
        }
    }
}
