package app.naum.myapplication.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.naum.myapplication.network.models.CryptoModel
import app.naum.myapplication.views.CoinGeneralInfoFragment
import app.naum.myapplication.views.CoinGraphViewFragment

class CoinOverviewViewPagerAdapter(
    fragment: Fragment,
    private val model: CryptoModel
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if(position == 0)
            CoinGeneralInfoFragment(model)
        else CoinGraphViewFragment(model)
    }
}