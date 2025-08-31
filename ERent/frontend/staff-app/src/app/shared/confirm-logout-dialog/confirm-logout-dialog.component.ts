import { Component, inject } from '@angular/core';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-confirm-logout-dialog',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, MatIconModule],
  template: `
    <div class="dlg">
      <div class="dlg-header">
        <mat-icon>logout</mat-icon>
        <h2>Logout</h2>
      </div>

      <div class="dlg-content">
        <p>Are you sure you want to log out?</p>
      </div>

      <div class="dlg-actions">
        <button mat-button class="cancel-btn" (click)="close(false)">No</button>
        <button mat-raised-button color="primary" class="confirm-btn" (click)="close(true)">
          Yes
        </button>
      </div>
    </div>
  `,
  styles: [`
    .dlg {
      padding: 24px 28px 20px;
      width: 460px;
      max-width: 95vw;
      border-radius: 16px;
    }

    .dlg-header {
      display: flex;
      align-items: center;
      gap: 12px;
      margin-bottom: 12px;
    }

    .dlg-header mat-icon {
      font-size: 30px;
      width: 30px;
      height: 30px;
      color: #2e6f6a;
    }

    .dlg-header h2 {
      margin: 0;
      font-size: 22px;
      font-weight: 700;
      color: #0f172a;
    }

    .dlg-content {
      color: #334155;
      margin: 12px 0 8px;
      font-size: 15px;
    }

    .dlg-actions {
      display: flex;
      justify-content: flex-end;
      gap: 12px;
      margin-top: 20px;
    }

    .dlg-actions button {
      min-width: 90px;
      border-radius: 12px !important;
      padding: 6px 18px;
      font-size: 15px;
      font-weight: 500;
      text-transform: none; 
    }

    .cancel-btn {
      color: #475569;
    }

    .confirm-btn {
      background-color: #2e6f6a !important;
      color: #fff !important;
    }
  `]
})
export class ConfirmLogoutDialogComponent {
  private ref = inject(MatDialogRef<ConfirmLogoutDialogComponent>);
  close(result: boolean) { this.ref.close(result); }
}
