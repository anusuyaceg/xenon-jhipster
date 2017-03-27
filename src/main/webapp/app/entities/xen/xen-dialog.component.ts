import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Xen } from './xen.model';
import { XenPopupService } from './xen-popup.service';
import { XenService } from './xen.service';
import { Category, CategoryService } from '../category';

@Component({
    selector: 'jhi-xen-dialog',
    templateUrl: './xen-dialog.component.html'
})
export class XenDialogComponent implements OnInit {

    xen: Xen;
    authorities: any[];
    isSaving: boolean;

    categories: Category[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private xenService: XenService,
        private categoryService: CategoryService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.categoryService.query().subscribe(
            (res: Response) => { this.categories = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.xen.id !== undefined) {
            this.xenService.update(this.xen)
                .subscribe((res: Xen) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.xenService.create(this.xen)
                .subscribe((res: Xen) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Xen) {
        this.eventManager.broadcast({ name: 'xenListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-xen-popup',
    template: ''
})
export class XenPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private xenPopupService: XenPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.xenPopupService
                    .open(XenDialogComponent, params['id']);
            } else {
                this.modalRef = this.xenPopupService
                    .open(XenDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
