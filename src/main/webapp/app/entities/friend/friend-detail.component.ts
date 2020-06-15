import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFriend } from 'app/shared/model/friend.model';

@Component({
  selector: 'jhi-friend-detail',
  templateUrl: './friend-detail.component.html'
})
export class FriendDetailComponent implements OnInit {
  friend: IFriend;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ friend }) => {
      this.friend = friend;
    });
  }

  previousState() {
    window.history.back();
  }
}
