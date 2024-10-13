import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private client?: Client;
  public notificationSubscription: Subject<string> = new Subject<string>();

  constructor() {
    this.client = new Client({
      brokerURL: 'ws://localhost:8080/ws', // Asegúrate de que esta URL sea correcta
      onConnect: () => {
        console.log('Conectado al WebSocket');
        this.subscribeToNotifications();
      },
      onStompError: (frame) => {
        console.error('Error de STOMP: ', frame.headers['message']);
      },
    });

    this.client.activate();
  }

  private subscribeToNotifications() {
    if (!this.client) {
      console.error('no client');
      return;
    }
    this.client.subscribe('/topic/notifications', (message) => {
      this.notificationSubscription.next(message.body);
    });
  }
}
