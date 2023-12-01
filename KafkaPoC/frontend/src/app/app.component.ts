import { Component } from "@angular/core";
import { StompService } from "@stomp/ng2-stompjs";
import { AppService } from "./app.service";

@Component( {
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: [ "./app.component.scss" ]
} )
export class AppComponent {
  messageSendToTest: string = "";
  messageSendToTestStream: string = "";
  filter: string = "";

  messagesReceivedFromTest: string[] = [];
  messagesReceivedFromTestStream: string[] = [];
  messagesReceivedFromTestStreamOutput: string[] = [];
  selectResult: string[] = [];

  constructor(
    private service: AppService,
    private stompService: StompService,
  ) {
    this.stompService.subscribe( "/topic/consumer" )
      .subscribe( ( notification ) => {
        this.messagesReceivedFromTest.unshift( notification.body );
      } );
    this.stompService.subscribe( "/topic/stream-consumer" )
      .subscribe( ( notification ) => {
        this.messagesReceivedFromTestStream.unshift( notification.body );
      } );
    this.stompService.subscribe( "/topic/stream-output-consumer" )
      .subscribe( ( notification ) => {
        this.messagesReceivedFromTestStreamOutput.unshift( notification.body );
      } );
  }

  async sendSync() {
    await this.service.sendSync( this.messageSendToTest );
  }

  async sendAsync() {
    await this.service.sendAsync( this.messageSendToTest );
  }

  async sendStream() {
    await this.service.sendToStream( this.messageSendToTestStream );
  }

  sendSelect() {
    this.service.sendSelect( this.filter ).then( r => this.selectResult = r );
  }

  async sendBulkToStream() {
    for ( let index = 1; index <= 100; index++ ) {
      await this.service.sendToStream( "Message_" + index );
    }
  }
}
