package code.shubham.core.lock.web.v1.validators;

import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import code.shubham.core.lockmodels.UnlockRequestDTO;

import java.util.Objects;

public class UnlockRequestValidator extends AbstractRequestValidator<UnlockRequestDTO> {

	@Override
	public IValidator<UnlockRequestDTO> validate(final UnlockRequestDTO request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getOwner()))
			this.putMessage("owner", MUST_NOT_BE_EMPTY, "owner");

		if (Objects.isNull(request.getVersion()))
			this.putMessage("version", MUST_NOT_BE_EMPTY, "version");

		return this;
	}

}
