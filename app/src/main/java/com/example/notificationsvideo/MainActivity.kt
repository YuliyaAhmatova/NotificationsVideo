package com.example.notificationsvideo

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notificationsvideo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var counter = 101

    companion object {
        const val NOTIFICATION_ID = 152
        const val CHANNEL_ID = "channelID"
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        createNotificationChannel()
        binding.simpleNotification.setOnClickListener {
            sendNotification()
        }
        binding.bigTextStyleNotification.setOnClickListener {
            bigTextNotification()
        }
        binding.bigPicturesStyleNotification.setOnClickListener {
            bigPicturesNotification()
        }
        binding.inboxStyleNotification.setOnClickListener {
            inboxNotification()
        }
        binding.actionNotification.setOnClickListener {
            actionNotification()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Уведомление",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


    @SuppressLint("InlinedApi")
    private fun sendNotification() {
        val link = "https://mail.ru/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_browser)
            .setContentTitle("Напоминание")
            .setContentText("Перейдите на сайт")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
            return
        }

        with(NotificationManagerCompat.from(this)) {
            notify(counter++, builder.build())
        }
    }

    private fun bigTextNotification() {
        val link = "https://mail.ru/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_browser)
            .setContentTitle("Напоминание")
            .setContentText("Перейдите на сайт")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Здесь должен быть большой текст"))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
            return
        }

        with(NotificationManagerCompat.from(this)) {
            notify(counter++, builder.build())
        }
    }

    private fun bigPicturesNotification() {
        val options = BitmapFactory.Options()
        options.inSampleSize = 2 // Уменьшить размер картинки в два раза
        val bigPicture = BitmapFactory.decodeResource(
            resources,
            R.drawable.f4eb39e14a407379cf67c970ec6105cb,
            options
        )

        val link = "https://mail.ru/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_browser)
            .setContentTitle("Напоминание")
            .setContentText("Перейдите на сайт")
            .setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(bigPicture)
                    .setBigContentTitle("Картинка")
            )
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
            return
        }

        with(NotificationManagerCompat.from(this)) {
            notify(counter++, builder.build())
        }
    }

    private fun inboxNotification() {

        val link = "https://mail.ru/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_browser)
            .setContentTitle("Напоминание")
            .setContentText("Перейдите на сайт")
        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.setBigContentTitle("Напоминания")
        inboxStyle.setSummaryText("Суммарное описание")

        // Добавляем тексты уведомлений в стиль
        inboxStyle.addLine("Текст первого уведомления")
        inboxStyle.addLine("Текст второго уведомления")
        inboxStyle.addLine("Текст третьего уведомления")

        // Добавляем стиль к уведомлению
        builder.setStyle(inboxStyle)

        builder.setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
            return
        }

        with(NotificationManagerCompat.from(this)) {
            notify(counter++, builder.build())
        }
    }

    private fun actionNotification() {
        val notificationId = counter++ // Используем текущий счетчик как ID уведомления

        // Создаем Intent для удаления уведомления
        val deleteIntent = Intent(this, NotificationReceiver::class.java).apply {
            action = "DELETE_NOTIFICATION"
            putExtra("notification_id", notificationId) // Передаем ID уведомления
        }

        val deletePendingIntent = PendingIntent.getBroadcast(
            this,
            notificationId, // Используем ID уведомления для PendingIntent
            deleteIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val link = "https://mail.ru/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        val pendingIntent =
            PendingIntent.getActivity(this, notificationId, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_browser)
            .setContentTitle("Напоминание")
            .setContentText("Перейдите на сайт")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_open, "Открыть", pendingIntent)
            .addAction(R.drawable.ic_delete, "Удалить", deletePendingIntent)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
            return
        }

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                sendNotification()
            }
        }
    }
}
