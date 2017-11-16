package edu.gvsu.restapi;

import java.util.Collection;
import java.util.List;
import org.json.JSONArray;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.resource.Post;
import org.restlet.resource.Get;
import com.googlecode.objectify.ObjectifyService;

/**
 * Represents a collection of widgets.  This resource processes HTTP requests that come in on the URIs
 * in the form of:
 *
 * http://host:port/widgets
 *
 * This resource supports both HTML and JSON representations.
 *
 * @author Jonathan Engelsma (http://themobilemontage.com)
 *
 */
public class WidgetsResource extends ServerResource {

	private List<Widget> widgets = null;

	@SuppressWarnings("unchecked")
	@Override
	public void doInit() {

    this.widgets = ObjectifyService.ofy()
        .load()
        .type(Widget.class) // We want only Widgets
        .list();

		// these are the representation types this resource can use to describe the
		// set of widgets with.
		getVariants().add(new Variant(MediaType.TEXT_HTML));
		getVariants().add(new Variant(MediaType.APPLICATION_JSON));

	}



	/**
	 * Handle an HTTP GET. Represent the widget object in the requested format.
	 *
	 * @param variant
	 * @return
	 * @throws ResourceException
	 */
	@Get
	public Representation get(Variant variant) throws ResourceException {
		Representation result = null;
		if (null == this.widgets) {
			ErrorMessage em = new ErrorMessage();
			return representError(variant, em);
		} else {

			if (variant.getMediaType().equals(MediaType.APPLICATION_JSON)) {

				JSONArray widgetArray = new JSONArray();
				for(Object o : this.widgets) {
					Widget w = (Widget)o;
					widgetArray.put(w.toJSON());
				}

				result = new JsonRepresentation(widgetArray);

			} else {

				// create a plain text representation of our list of widgets
				StringBuffer buf = new StringBuffer("<html><head><title>Widget Resources</title><head><body><h1>Widget Resources</h1>");
				buf.append("<form name=\"input\" action=\"/widgets\" method=\"POST\">");
				buf.append("Widget name: ");
				buf.append("<input type=\"text\" name=\"name\" />");
				buf.append("<input type=\"submit\" value=\"Create\" />");
				buf.append("</form>");
				buf.append("<br/><h2> There are " + this.widgets.size() + " total.</h2>");
				for(Object o : this.widgets) {
					Widget w = (Widget)o;
					buf.append(w.toHtml(true));
				}
				buf.append("</body></html>");
				result = new StringRepresentation(buf.toString());
				result.setMediaType(MediaType.TEXT_HTML);
			}
		}
		return result;
	}

	/**
	 * Handle a POST Http request. Create a new widget
	 *
	 * @param entity
	 * @throws ResourceException
	 */
	@Post
	public Representation post(Representation entity, Variant variant)
		throws ResourceException
	{

		Representation rep = null;

		// We handle only a form request in this example. Other types could be
		// JSON or XML.
		try {
			if (entity.getMediaType().equals(MediaType.APPLICATION_WWW_FORM,
					true))
			{
				// Use the incoming data in the POST request to create/store a new widget resource.
				Form form = new Form(entity);
				Widget w = new Widget();
				w.setName(form.getFirstValue("name"));

        // persist updated object
        ObjectifyService.ofy().save().entity(w).now();

				getResponse().setStatus(Status.SUCCESS_OK);
				rep = new StringRepresentation(w.toHtml(false));
				rep.setMediaType(MediaType.TEXT_HTML);
				getResponse().setEntity(rep);

			} else {
				getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			}
		} catch (Exception e) {
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
		}
		return rep;
	}

	/**
	 * Represent an error message in the requested format.
	 *
	 * @param variant
	 * @param em
	 * @return
	 * @throws ResourceException
	 */
	private Representation representError(Variant variant, ErrorMessage em)
	throws ResourceException {
		Representation result = null;
		if (variant.getMediaType().equals(MediaType.APPLICATION_JSON)) {
			result = new JsonRepresentation(em.toJSON());
		} else {
			result = new StringRepresentation(em.toString());
		}
		return result;
	}

	protected Representation representError(MediaType type, ErrorMessage em)
	throws ResourceException {
		Representation result = null;
		if (type.equals(MediaType.APPLICATION_JSON)) {
			result = new JsonRepresentation(em.toJSON());
		} else {
			result = new StringRepresentation(em.toString());
		}
		return result;
	}
}
