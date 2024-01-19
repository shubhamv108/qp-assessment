package code.shubham.core.iamcommons;

import code.shubham.core.iammodels.GetOrCreateUser;
import code.shubham.core.iammodels.GetUserResponse;

public interface IUserService {

	GetUserResponse getOrCreate(GetOrCreateUser.Request request);

	GetUserResponse fetchById(String userId);

}
