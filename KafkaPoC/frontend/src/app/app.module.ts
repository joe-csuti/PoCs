import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { StompConfig } from "@stomp/ng2-stompjs";
import { StompService } from "@stomp/ng2-stompjs";
import { environment } from "../environments/environment";

import { AppComponent } from "./app.component";

const stompConfig: StompConfig = {
  debug: false,
  headers: {},
  heartbeat_in: 0,
  heartbeat_out: 0,
  reconnect_delay: 0,
  url: environment.stompUrl
};

export function StompFactory() {
  return new StompService( stompConfig );
}

@NgModule( {
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [
    {
      provide: StompService,
      useFactory: StompFactory,
    }
  ],
  bootstrap: [ AppComponent ]
} )
export class AppModule {
}
