package com.example.quiz_app


import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class QuizQuestionActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    private var mCurrentPosition :Int = 1

    private var mQuestionList: ArrayList<Question>? = null

    private var mSelectedAnswer : Int  = 0

    //Create global variables for the views in the layout
    private var progressBar: ProgressBar?=null
    private var tvProgress: TextView? = null
    private var tvQuestion:TextView? = null
    private var ivImage: ImageView? = null
    private var tvOptionOne:TextView? = null
    private var tvOptionTwo:TextView? = null
    private var tvOptionThree:TextView? = null
    private var tvOptionFour:TextView? = null
    private var tvName:TextView? = null
    private var btnSubmit: Button? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sharedPreferenceManager = SharedPreferenceManager(this)
        setContentView(R.layout.activity_quiz_question)

        progressBar=findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)
        tvName = findViewById(R.id.tv_name)
        

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)

        btnSubmit?.setOnClickListener(this)

        mQuestionList = Constant.getQuestions()  // get result from api or static file

        setQuestion()


    }

    private fun setQuestion() {
        val question: Question = mQuestionList!![mCurrentPosition - 1]  // get result List of index
        defaultOptionView()

        if(mCurrentPosition == mQuestionList!!.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "SUBMIT"
        }

        val username = sharedPreferenceManager.getString("username")

        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        ivImage?.setImageResource(question.image)
        tvQuestion?.text = question.question
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour
        tvName?.text = username

    }

    override fun onClick(v: View?) {

        when(v?.id)  {
            R.id.tv_option_one -> {
                tvOptionOne?.let {
                    selectedOptionView(it,1)
                }
            }

            R.id.tv_option_two -> {
                tvOptionTwo?.let {
                    selectedOptionView(it,2)
                }
            }
            R.id.tv_option_three -> {
                tvOptionThree?.let {
                    selectedOptionView(it,3)
                }
            }
            R.id.tv_option_four-> {
                tvOptionFour?.let {
                    selectedOptionView(it,4)
                }
            }

            R.id.btn_submit -> {
                    if(mSelectedAnswer == 0 ) {

                        mCurrentPosition ++

                        when{
                            mCurrentPosition <= mQuestionList!!.size -> {
                                setQuestion()
                            } else -> {
                                Toast.makeText(this@QuizQuestionActivity,"You made it to the end",Toast.LENGTH_LONG)
                            }
                        }
                    } else  {
                        // correct answer logic
                       val question =  mQuestionList?.get(mCurrentPosition - 1)
                        if(question!!.correctAnswer != mSelectedAnswer) { //This to check correct answer or not
                            answerView(mSelectedAnswer,R.drawable.wrong_answer_border)
                        }

                        answerView(question.correctAnswer,R.drawable.correct_option_border) // this is for correct answer

                        if(mCurrentPosition == mQuestionList!!.size) {
                            btnSubmit?.text = "FINISH"
                        } else {
                            btnSubmit?.text = "GO TO NEXT QUESTION"
                        }

                        mSelectedAnswer = 0

                    }

            }
        }

    }


    private fun answerView(answer : Int, drawableView : Int) {

        when(answer) {
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(this, drawableView)
            }
        }

    }

    private fun selectedOptionView(tv:TextView,selectedIndex:Int) {
        defaultOptionView()
        mSelectedAnswer = selectedIndex
        tv.setTextColor(Color.parseColor("#FFFFFF"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.selected_option_border)
    }

    private fun defaultOptionView() {
        val options = ArrayList<TextView>()
        tvOptionOne?.let {  //let,run ,apply,with, also  is scope function
            options.add(0,it)
        }
        tvOptionTwo?.let {
            options.add(1,it)
        }
        tvOptionThree?.let {
            options.add(2,it)
        }
        tvOptionFour?.let {
            options.add(3,it)
        }
        for(option in options ){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,R.drawable.default_option_border_bg
            )
        }
    }
}