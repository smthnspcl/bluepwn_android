package io.eberlein.insane.bluepwn.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.eberlein.insane.bluepwn.fragment.ServiceTabCharacteristicsFragment;
import io.eberlein.insane.bluepwn.fragment.ServiceTabInfoFragment;
import io.eberlein.insane.bluepwn.fragment.ServiceTabStagersFragment;
import io.eberlein.insane.bluepwn.object.Service;


public class ServiceFragmentPagerAdapter extends FragmentPagerAdapter {
    private Service service;
    private final String[] tabNames = {"nfo", "stgrs", "chrctrstcs"};

    public ServiceFragmentPagerAdapter(FragmentManager fm, Service service) {
        super(fm);
        this.service = service;
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ServiceTabInfoFragment.newInstance(position + 1, service);
            case 1:
                return ServiceTabStagersFragment.newInstance(position + 1, service);
            case 2:
                return ServiceTabCharacteristicsFragment.newInstance(position + 1, service);
            default:
                return ServiceTabInfoFragment.newInstance(position + 1, service);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }
}
