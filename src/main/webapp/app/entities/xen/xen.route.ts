import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { XenComponent } from './xen.component';
import { XenDetailComponent } from './xen-detail.component';
import { XenPopupComponent } from './xen-dialog.component';
import { XenDeletePopupComponent } from './xen-delete-dialog.component';

import { Principal } from '../../shared';


export const xenRoute: Routes = [
  {
    path: 'xen',
    component: XenComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Xens'
    }
  }, {
    path: 'xen/:id',
    component: XenDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Xens'
    }
  }
];

export const xenPopupRoute: Routes = [
  {
    path: 'xen-new',
    component: XenPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Xens'
    },
    outlet: 'popup'
  },
  {
    path: 'xen/:id/edit',
    component: XenPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Xens'
    },
    outlet: 'popup'
  },
  {
    path: 'xen/:id/delete',
    component: XenDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Xens'
    },
    outlet: 'popup'
  }
];
