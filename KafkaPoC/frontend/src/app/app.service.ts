import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { environment } from "../environments/environment";

@Injectable( {
  providedIn: "root"
} )
export class AppService {
  constructor(
    private httpClient: HttpClient,
    private baseUrl = environment.baseUrl
  ) {
  }

  public async sendSync( message: string ): Promise<void> {
    return firstValueFrom( this.httpClient.post<void>( this.baseUrl + "/send-sync", message ) );
  }

  public async sendAsync( message: string ): Promise<void> {
    return firstValueFrom( this.httpClient.post<void>( this.baseUrl + "/send-async", message ) );
  }

  public async sendToStream( message: string ): Promise<void> {
    return firstValueFrom( this.httpClient.post<void>( this.baseUrl + "/send-stream", message ) );
  }

  public async sendSelect( message: string ): Promise<string[]> {
    return firstValueFrom( this.httpClient.post<string[]>( this.baseUrl + "/select", message ) );
  }
}
