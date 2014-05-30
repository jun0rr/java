PrimeFaces.widget.GMap=function(c){this.cfg=c;this.id=this.cfg.id;this.jqId=PrimeFaces.escapeClientId(this.id);this.jq=$(this.jqId);var b=this;if(this.jq.is(":visible")){this.init()}else{var a=this.jq.parents(".ui-hidden-container:first"),d=a.data("widget");if(d){d.addOnshowHandler(function(){return b.init()})}}this.postConstruct()};PrimeFaces.extend(PrimeFaces.widget.GMap,PrimeFaces.widget.BaseWidget);PrimeFaces.widget.GMap.prototype.init=function(){this.map=new google.maps.Map(document.getElementById(this.id),this.cfg);this.cfg.fitBounds=!(this.cfg.fitBounds===false);this.viewport=this.map.getBounds();if(this.cfg.markers){this.configureMarkers()}if(this.cfg.polylines){this.configurePolylines()}if(this.cfg.polygons){this.configurePolygons()}if(this.cfg.circles){this.configureCircles()}if(this.cfg.rectangles){this.configureRectangles()}this.configureEventListeners();if(this.cfg.fitBounds&&this.viewport){this.map.fitBounds(this.viewport)}if(this.cfg.infoWindow){var a=this;google.maps.event.addListener(this.cfg.infoWindow,"domready",function(){a.loadWindow(a.cfg.infoWindowContent)})}};PrimeFaces.widget.GMap.prototype.getMap=function(){return this.map};PrimeFaces.widget.GMap.prototype.getInfoWindow=function(){return this.cfg.infoWindow};PrimeFaces.widget.GMap.prototype.loadWindow=function(a){this.jq.find(PrimeFaces.escapeClientId(this.getInfoWindow().id+"_content")).html(a||"")};PrimeFaces.widget.GMap.prototype.openWindow=function(f){var d=$(f.documentElement),e=d.find("update"),b=this.getInfoWindow();for(var a=0;a<e.length;a++){var h=e.eq(a),g=h.attr("id"),c=h.text();if(g==b.id){this.cfg.infoWindowContent=c;b.setContent('<div id="'+g+'_content">'+c+"</div>");b.open(this.getMap(),this.selectedOverlay)}else{PrimeFaces.ajax.AjaxUtils.updateElement.call(this,g,c)}}PrimeFaces.ajax.AjaxUtils.handleResponse.call(this,d);return true};PrimeFaces.widget.GMap.prototype.configureMarkers=function(){var a=this;for(var c=0;c<this.cfg.markers.length;c++){var b=this.cfg.markers[c];b.setMap(this.map);if(this.cfg.fitBounds){this.extendView(b)}google.maps.event.addListener(b,"click",function(d){a.fireOverlaySelectEvent(d,this)});google.maps.event.addListener(b,"dragend",function(d){a.fireMarkerDragEvent(d,this)})}};PrimeFaces.widget.GMap.prototype.fireMarkerDragEvent=function(c,a){if(this.hasBehavior("markerDrag")){var d=this.cfg.behaviors.markerDrag;var b={params:{}};b.params[this.id+"_markerId"]=a.id;b.params[this.id+"_lat"]=c.latLng.lat();b.params[this.id+"_lng"]=c.latLng.lng();d.call(this,c,b)}};PrimeFaces.widget.GMap.prototype.configurePolylines=function(){this.addOverlays(this.cfg.polylines)};PrimeFaces.widget.GMap.prototype.configureCircles=function(){this.addOverlays(this.cfg.circles)};PrimeFaces.widget.GMap.prototype.configureRectangles=function(){this.addOverlays(this.cfg.rectangles)};PrimeFaces.widget.GMap.prototype.configurePolygons=function(){this.addOverlays(this.cfg.polygons)};PrimeFaces.widget.GMap.prototype.fireOverlaySelectEvent=function(d,b){this.selectedOverlay=b;if(this.hasBehavior("overlaySelect")){var a=this.cfg.behaviors.overlaySelect;var c={params:{}};c.params[this.id+"_overlayId"]=b.id;a.call(this,d,c)}};PrimeFaces.widget.GMap.prototype.configureEventListeners=function(){var a=this;this.cfg.formId=$(PrimeFaces.escapeClientId(this.id)).parents("form:first").attr("id");if(this.cfg.onPointClick){google.maps.event.addListener(this.map,"click",function(b){a.cfg.onPointClick(b)})}this.configureStateChangeListener();this.configurePointSelectListener()};PrimeFaces.widget.GMap.prototype.configureStateChangeListener=function(){var a=this,b=function(c){a.fireStateChangeEvent(c)};google.maps.event.addListener(this.map,"zoom_changed",b);google.maps.event.addListener(this.map,"dragend",b)};PrimeFaces.widget.GMap.prototype.fireStateChangeEvent=function(c){if(this.hasBehavior("stateChange")){var a=this.cfg.behaviors.stateChange;var b={params:{}};b.params[this.id+"_northeast"]=this.map.getBounds().getNorthEast().lat()+","+this.map.getBounds().getNorthEast().lng();b.params[this.id+"_southwest"]=this.map.getBounds().getSouthWest().lat()+","+this.map.getBounds().getSouthWest().lng();b.params[this.id+"_center"]=this.map.getBounds().getCenter().lat()+","+this.map.getBounds().getCenter().lng();b.params[this.id+"_zoom"]=this.map.getZoom();a.call(this,c,b)}};PrimeFaces.widget.GMap.prototype.configurePointSelectListener=function(){var a=this;google.maps.event.addListener(this.map,"click",function(b){a.firePointSelectEvent(b)})};PrimeFaces.widget.GMap.prototype.firePointSelectEvent=function(b){if(this.hasBehavior("pointSelect")){var c=this.cfg.behaviors.pointSelect;var a={params:{}};a.params[this.id+"_pointLatLng"]=b.latLng.lat()+","+b.latLng.lng();c.call(this,b,a)}};PrimeFaces.widget.GMap.prototype.addOverlay=function(a){a.setMap(this.map)};PrimeFaces.widget.GMap.prototype.addOverlays=function(b){var a=this;$.each(b,function(c,d){d.setMap(a.map);a.extendView(d);google.maps.event.addListener(d,"click",function(e){a.fireOverlaySelectEvent(e,d)})})};PrimeFaces.widget.GMap.prototype.extendView=function(b){if(this.cfg.fitBounds&&b){var a=this;this.viewport=this.viewport||new google.maps.LatLngBounds();if(b instanceof google.maps.Marker){this.viewport.extend(b.getPosition())}else{if(b instanceof google.maps.Circle||b instanceof google.maps.Rectangle){this.viewport.union(b.getBounds())}else{if(b instanceof google.maps.Polyline||b instanceof google.maps.Polygon){b.getPath().forEach(function(d,c){a.viewport.extend(d)})}}}}};PrimeFaces.widget.GMap.prototype.checkResize=function(){google.maps.event.trigger(this.map,"resize");this.map.setZoom(this.map.getZoom())};PrimeFaces.widget.GMap.prototype.hasBehavior=function(a){if(this.cfg.behaviors){return this.cfg.behaviors[a]!=undefined}return false};