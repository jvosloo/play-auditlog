package play.modules.auditlog;

public interface IActorProvider {
	public String getActor();
	public Long getUserId();
	public Long getAccountId();
}
