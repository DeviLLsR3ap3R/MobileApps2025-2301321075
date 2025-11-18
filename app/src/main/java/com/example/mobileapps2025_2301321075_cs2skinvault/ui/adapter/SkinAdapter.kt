package com.example.mobileapps2025_2301321075_cs2skinvault.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mobileapps2025_2301321075_cs2skinvault.R
import com.example.mobileapps2025_2301321075_cs2skinvault.data.models.Skin

class SkinAdapter : ListAdapter<Skin, SkinAdapter.VH>(DIFF) {
    var lambdaOnClick: ((Skin?) -> Unit)? = null
    object DIFF : DiffUtil.ItemCallback<Skin>() {
        override fun areItemsTheSame(oldItem: Skin, newItem: Skin) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Skin, newItem: Skin) = oldItem == newItem
    }

    class ItemSkinBinding private constructor(val root: View) {
        val tvName: TextView = root.findViewById(R.id.tvSkinName)
        val tvWeapon: TextView = root.findViewById(R.id.tvWeapon)
        val tvRarity: TextView = root.findViewById(R.id.tvRarity)
        val tvPrice: TextView = root.findViewById(R.id.tvPrice)
        val img: ImageView = root.findViewById(R.id.ivSkinImage)

        companion object {
            fun inflate(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): ItemSkinBinding {
                val view = inflater.inflate(R.layout.item_skin, parent, attachToParent)
                return ItemSkinBinding(view)
            }
        }
    }

    inner class VH(val b: ItemSkinBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemSkinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val skin = getItem(position)
        holder.b.tvName.text = skin.name
        holder.b.tvWeapon.text = skin.weapon
        holder.b.tvRarity.text = skin.rarity
        holder.b.tvPrice.text = "$${skin.price}"

        if (!skin.imageUrl.isNullOrBlank()) {
            holder.b.img.load(skin.imageUrl)
        } else {
            holder.b.img.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        holder.itemView.setOnClickListener {
            lambdaOnClick?.let { it(skin) }
        }
    }
}
