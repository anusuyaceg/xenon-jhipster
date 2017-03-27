import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { XenDetailComponent } from '../../../../../../main/webapp/app/entities/xen/xen-detail.component';
import { XenService } from '../../../../../../main/webapp/app/entities/xen/xen.service';
import { Xen } from '../../../../../../main/webapp/app/entities/xen/xen.model';

describe('Component Tests', () => {

    describe('Xen Management Detail Component', () => {
        let comp: XenDetailComponent;
        let fixture: ComponentFixture<XenDetailComponent>;
        let service: XenService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [XenDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    XenService
                ]
            }).overrideComponent(XenDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(XenDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(XenService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Xen(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.xen).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
