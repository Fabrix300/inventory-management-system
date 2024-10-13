import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebSocketService } from '../websocket.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css',
})
export class NotificationsComponent implements OnInit {
  public notifications: string[] = [];

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit() {
    this.webSocketService.notificationSubscription.subscribe((notification) => {
      this.notifications.push(notification);
    });
  }
}
