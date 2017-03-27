import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Xen } from './xen.model';
import { XenService } from './xen.service';

@Component({
    selector: 'jhi-xen-detail',
    templateUrl: './xen-detail.component.html'
})
export class XenDetailComponent implements OnInit, OnDestroy {

    xen: Xen;
    private subscription: any;

    constructor(
        private xenService: XenService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.xenService.find(id).subscribe(xen => {
            this.xen = xen;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
