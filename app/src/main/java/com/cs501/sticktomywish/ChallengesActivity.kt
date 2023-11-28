package com.cs501.sticktomywish

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChallengesActivity : AppCompatActivity() {
    private lateinit var challengesAdapter: ChallengesAdapter
    private lateinit var challengesRecyclerView: RecyclerView
    private lateinit var addChallengeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        challengesRecyclerView = findViewById(R.id.challengesRecyclerView)
        addChallengeButton = findViewById(R.id.addChallengeButton)

        // Initialize the adapter with an empty list
        challengesAdapter = ChallengesAdapter(mutableListOf())

        // Setup the RecyclerView
        challengesRecyclerView.adapter = challengesAdapter
        challengesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the button click listener to add a new challenge
        addChallengeButton.setOnClickListener {
            // This is where you would add a new challenge, probably through a dialog
            Log.d("ChallengesActivity", "Add challenge clicked")
            showAddChallengeDialog()
        }
    }

    private fun showAddChallengeDialog() {
        // Create a layout for the dialog
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        // Create an EditText for the challenge name input
        val nameInputField = EditText(this)
        nameInputField.hint = "Enter the name of the challenge"

        // Create an EditText for the number of days input
        val daysInputField = EditText(this)
        daysInputField.inputType = InputType.TYPE_CLASS_NUMBER
        daysInputField.hint = "Enter the number of days"

        // Add the EditTexts to the layout
        layout.addView(nameInputField)
        layout.addView(daysInputField)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(50, 0, 50, 0) // Adjust the margins as needed
        nameInputField.layoutParams = layoutParams
        daysInputField.layoutParams = layoutParams

        // Create and show the AlertDialog
        AlertDialog.Builder(this)
            .setTitle("New Challenge")
            .setMessage("Fill in the details for the new challenge:")
            .setView(layout)
            .setPositiveButton("Add") { dialog, which ->
                val name = nameInputField.text.toString()
                val days = daysInputField.text.toString().toIntOrNull()
                if (name.isNotBlank() && days != null) {
                    val challengeName = "$name - $days days"
                    val newChallenge = Challenge(challengeName, false)
                    challengesAdapter.addChallenge(newChallenge)
                } else {
                    Toast.makeText(this, "Please enter the challenge name and number of days", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }


}

class ChallengesAdapter(private val challenges: MutableList<Challenge>) : RecyclerView.Adapter<ChallengesAdapter.ChallengeViewHolder>() {

    class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val challengeText: TextView = itemView.findViewById(R.id.challengeText)
        val challengeCheckBox: CheckBox = itemView.findViewById(R.id.challengeCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = challenges[position]
        holder.challengeText.text = challenge.name
        holder.challengeCheckBox.isChecked = challenge.isCompleted
    }

    override fun getItemCount() = challenges.size

    fun addChallenge(challenge: Challenge) {
        challenges.add(challenge)
        notifyItemInserted(challenges.size - 1)
    }
}

data class Challenge(val name: String, var isCompleted: Boolean)
