package com.haohuoke.core.ui

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.blankj.utilcode.util.ThreadUtils
import com.haohuoke.core.HKAccessibility

object UIOperate {
	
	fun findById(id: String): List<AccessibilityNodeInfo> {
		HKAccessibility.service?.rootInActiveWindow?.findAccessibilityNodeInfosByViewId(id)?.let {
			return it
		}
		return arrayListOf()
	}
	
	fun findByTags(
		className: String,
		parentNode: AccessibilityNodeInfo? = null
	): List<AccessibilityNodeInfo> {
		val nodeList = arrayListOf<AccessibilityNodeInfo>()
		parentNode?.let {
			getNodes(parentNode).forEach {
				if (TextUtils.equals(className, it.className)) {
					nodeList.add(it)
				}
			}
		} ?: let {
			getAllNodes().forEach {
				if (TextUtils.equals(className, it.className)) {
					nodeList.add(it)
				}
			}
		}
		return nodeList
	}
	fun findByText(text: String?): List<AccessibilityNodeInfo> {
		HKAccessibility.service?.rootInActiveWindow?.findAccessibilityNodeInfosByText(text)?.let {
			return it
		}
		return arrayListOf()
	}
	
	fun findParentClickable(
		nodeInfo: AccessibilityNodeInfo,
		callback: (nodeInfo: AccessibilityNodeInfo) -> Unit
	) {
		if (nodeInfo == null) {
			return
		}
		if (nodeInfo.parent == null) {
			return
		}
		if (nodeInfo.parent.isClickable) {
			callback.invoke(nodeInfo.parent)
			return
		} else {
			findParentClickable(nodeInfo.parent, callback)
		}
	}
	
	fun getAllNodes(): ArrayList<AccessibilityNodeInfo> {
		val nodeList = arrayListOf<AccessibilityNodeInfo>()
		HKAccessibility.service?.rootInActiveWindow?.let {
			getNodes(it, nodeList)
		}
		return nodeList
	}
	
	fun getNodes(parentNode: AccessibilityNodeInfo): ArrayList<AccessibilityNodeInfo> {
		val nodeList = arrayListOf<AccessibilityNodeInfo>()
		getNodes(parentNode, nodeList)
		return nodeList
	}
	
	private fun getNodes(
		parentNode: AccessibilityNodeInfo,
		nodeList: ArrayList<AccessibilityNodeInfo>
	) {
		nodeList.add(parentNode)
		if (nodeList.size > 10000) return
		for (index in 0 until parentNode.childCount) {
			parentNode.getChild(index)?.let {
				getNodes(it, nodeList)
			}
		}
	}
	
	
	fun gesture(
		startLocation: FloatArray,
		endLocation: FloatArray,
		startTime: Long,
		duration: Long,
	): Long {
		val delay: Long =
			HKAccessibility.ListenerManager.gestureListener?.onGestureBegin(startLocation, endLocation) ?: 0
		val path = Path()
		path.moveTo(startLocation[0], startLocation[1])
		path.lineTo(endLocation[0], endLocation[1])
		val builder = GestureDescription.Builder()
		val strokeDescription = GestureDescription.StrokeDescription(path, startTime, duration)
		val gestureDescription = builder.addStroke(strokeDescription).build()
		object : AccessibilityService.GestureResultCallback() {
			override fun onCompleted(gestureDescription: GestureDescription) {
				HKAccessibility.ListenerManager.gestureListener?.onGestureCompleted()
				HKAccessibility.ListenerManager.gestureListener?.onGestureEnd()
			}
			
			override fun onCancelled(gestureDescription: GestureDescription) {
				HKAccessibility.ListenerManager.gestureListener?.onGestureCancelled()
				HKAccessibility.ListenerManager.gestureListener?.onGestureEnd()
			}
		}.apply {
			if (delay == 0L) {
				HKAccessibility.service!!.dispatchGesture(gestureDescription, this, null)
			} else {
				ThreadUtils.runOnUiThreadDelayed({
					HKAccessibility.service!!.dispatchGesture(gestureDescription, this, null)
				}, delay)
			}
		}
		return delay + startTime + duration
	}
	
	
	fun clickScreen(
		x: Float,
		y: Float,
		duration: Long
	): Long {
		if(x<0||y<0){
			
			return 0
		}
		return gesture(
			floatArrayOf(x, y), floatArrayOf(x, y),
			0,
			duration,
		)
	}
	
	/**
	 * 返回
	 */
	fun back() {
		HKAccessibility.service?.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
	}

}
