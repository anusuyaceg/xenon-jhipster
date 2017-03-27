import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Xen } from './xen.model';
import { XenPopupService } from './xen-popup.service';
import { XenService } from './xen.service';

@Component({
    selector: 'jhi-xen-delete-dialog',
    templateUrl: './xen-delete-dialog.component.html'
})
export class XenDeleteDialogComponent {

    xen: Xen;

    constructor(
        private xenService: XenService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.xenService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'xenListModification',
                content: 'Deleted an xen'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-xen-delete-popup',
    template: ''
})
export class XenDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private xenPopupService: XenPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.xenPopupService
                .open(XenDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
