/*
 * Nextcloud Android client application
 *
 * @author Chris Narkiewicz
 *
 * Copyright (C) 2019 Chris Narkiewicz <hello@ezaquarii.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.nextcloud.client.device

import android.annotation.TargetApi
import android.os.Build
import android.os.PowerManager

internal class PowerManagementServiceImpl(
    private val powerManager: PowerManager,
    private val deviceInfo: DeviceInfo = DeviceInfo()
) : PowerManagementService {

    companion object {
        /**
         * Vendors on this list use aggressive power saving methods that might
         * break application experience.
         */
        val OVERLY_AGGRESSIVE_POWER_SAVING_VENDORS = setOf("samsung", "huawei", "xiaomi")
    }

    override val isPowerSavingEnabled: Boolean
        get() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            if (deviceInfo.apiLevel >= Build.VERSION_CODES.LOLLIPOP) {
                return powerManager.isPowerSaveMode
            }
            // For older versions, we just say that device is not in power save mode
            return false
        }

    override val isPowerSavingExclusionAvailable: Boolean
        get() = deviceInfo.vendor in OVERLY_AGGRESSIVE_POWER_SAVING_VENDORS
}
