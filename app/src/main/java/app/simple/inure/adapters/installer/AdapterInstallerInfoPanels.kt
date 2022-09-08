package app.simple.inure.adapters.installer

import androidx.fragment.app.Fragment
import app.simple.inure.extensions.adapters.BaseFragmentStateAdapter
import app.simple.inure.ui.installer.Information
import app.simple.inure.ui.installer.Manifest
import app.simple.inure.ui.installer.Permissions
import java.io.File

class AdapterInstallerInfoPanels(fragment: Fragment, private val file: File, private val titles: Array<String>) : BaseFragmentStateAdapter(fragment) {

    private val count = 3

    override fun getItemCount(): Int {
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Information.newInstance(file)
            1 -> Permissions.newInstance(file)
            2 -> Manifest.newInstance(file)
            else -> throw IllegalStateException("Invalid fragment range")
        }
    }

    override fun getPageTitle(position: Int): String {
        return titles[position]
    }
}